package app.cash.barber

import app.cash.barber.models.BarberKey
import app.cash.barber.models.Document
import app.cash.barber.models.DocumentData
import app.cash.barber.models.DocumentTemplate
import kotlin.reflect.KClass

/**
 * A registry of templates with their input document data types and their output document types.
 */
interface Barbershop {
  fun <DD : DocumentData, D : Document> getBarber(
    documentDataClass: KClass<out DD>,
    documentClass: KClass<out D>
  ): Barber<DD, D>

  fun <DD : DocumentData> getTargetDocuments(documentDataClass: KClass<out DD>):
    Set<KClass<out Document>>

  fun getAllBarbers(): Map<BarberKey, Barber<*, *>>

  fun getWarnings(): List<String>

  interface Builder {
    /**
     * Configures this barbershop so that instances of [documentDataClass] will rendered by
     * [documentTemplate] for its target locale.
     */
    fun installDocumentTemplate(
      documentDataClass: KClass<out DocumentData>,
      documentTemplate: DocumentTemplate
    ): Builder

    /**
     * Prepares this barbershop to render instances of [document].
     */
    fun installDocument(document: KClass<out Document>): Builder

    /**
     * Configures this barbershop to use [LocaleResolver] to map requested locales to available
     * templates. By default Barber does an exact match, and if nothing matches it uses the first
     * installed template.
     */
    fun setLocaleResolver(resolver: LocaleResolver): Builder

    /**
     * Configures this barbershop to treat warnings as errors during validataion. By default,
     * only errors, not warnings, lead to fatal BarberException during validation.
     */
    fun setWarningsAsErrors(): Builder

    /**
     * Validates that all templates, document datas, and documents are mutually consistent and
     * returns a new Barbershop.
     */
    fun build(): Barbershop
  }
}

inline fun <reified DD : DocumentData, reified D : Document> Barbershop.getBarber() = getBarber(
  DD::class, D::class)

inline fun <reified DD : DocumentData> Barbershop.getTargetDocuments() = getTargetDocuments(
  DD::class)
