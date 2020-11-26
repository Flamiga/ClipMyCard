package com.ebookfrenzy.clipmycard.Fragment

import android.app.Dialog
import android.graphics.Bitmap
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.fragment.app.DialogFragment
import com.ebookfrenzy.clipmycard.models.Card
import com.google.zxing.BarcodeFormat
import com.google.zxing.oned.Code128Writer
import kotlinx.android.synthetic.main.scanbar_layout.*

open class ScanbarFragment(card: Card) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

         fun createBarcodeBitmap(
            barcodeValue: String,
            @ColorInt barcodeColor: Int,
            @ColorInt backgroundColor: Int,
            widthPixels: Int,
            heightPixels: Int
        ): Bitmap {
            val bitMatrix = Code128Writer().encode(
                barcodeValue,
                BarcodeFormat.CODE_128,
                widthPixels,
                heightPixels
            )

            val pixels = IntArray(bitMatrix.width * bitMatrix.height)
            for (y in 0 until bitMatrix.height) {
                val offset = y * bitMatrix.width
                for (x in 0 until bitMatrix.width) {
                    pixels[offset + x] =
                        if (bitMatrix.get(x, y)) barcodeColor else backgroundColor
                }
            }

            val bitmap = Bitmap.createBitmap(
                bitMatrix.width,
                bitMatrix.height,
                Bitmap.Config.ARGB_8888
            )
            bitmap.setPixels(
                pixels,
                0,
                bitMatrix.width,
                0,
                0,
                bitMatrix.width,
                bitMatrix.height
            )
            return bitmap
        }
        return super.onCreateDialog(savedInstanceState)
    }

}