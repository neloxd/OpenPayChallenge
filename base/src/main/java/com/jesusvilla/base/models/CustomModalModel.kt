package com.jesusvilla.base.models

import android.view.View

class CustomModalModel private constructor(
    val title: CharSequence? = null,
    val body: CharSequence? = null,
    val modalOptions: ModalOptions = ModalOptions()
) {
    data class ModalOptions(
        val primaryButton: ModalButtonModel? = null,
    )

    class Builder() {
        private var title: CharSequence? = null
        private var body: CharSequence? = null
        private var modalOptions: ModalOptions = ModalOptions()

        fun title(value: CharSequence?) = apply { this.title = value }
        fun body(value: CharSequence?) = apply { this.body = value }

        fun primaryButton(value: ModalButtonModel) =
            apply { this.modalOptions = modalOptions.copy(primaryButton = value) }

        fun primaryButton(label: CharSequence, onClick: View.OnClickListener? = null) =
            apply { primaryButton(ModalButtonModel(label, onClick)) }
        fun build() =
            CustomModalModel(
                title,
                body,
                modalOptions
            )
    }
}
data class ModalButtonModel(
    val label: CharSequence?,
    var onClick: View.OnClickListener? = null
)
