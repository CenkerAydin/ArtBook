package com.cenkeraydin.artbook.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

object Util {

    const val API_KEY="48443601-d0d1e783087daaba6e250fc92"
    const val BASE_URL="https://pixabay.com"

    fun EditText.textChanges(): kotlinx.coroutines.flow.Flow<CharSequence?> = callbackFlow {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s).isSuccess
            }
            override fun afterTextChanged(s: Editable?) {}
        }
        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) } // Flow sonlandığında TextWatcher'ı kaldır
    }
}