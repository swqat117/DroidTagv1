package com.quascenta.petersroad.droidtag.Utils;

import com.quascenta.petersroad.droidtag.widgets.FormEditText;

/**
 * Created by AKSHAY on 3/28/2017.
 */

public class EmailProviderValidator {

    FormEditText editText;

    public EmailProviderValidator(FormEditText editText) {
        this.editText = editText;
    }


    public boolean isValid() {
        String x = editText.getText().toString();
        return !(x.contains("@gmail.") || x.contains("@yahoo.") || x.contains("@aol.") || x.contains("@mac.") || x.contains("@facebook.") || x.contains("@zoho.") || x.contains("@yandex.") || x.contains("@gmx.") || x.contains("@hush.")
                || x.contains("@outlook.")
                || x.contains("@rediff.")
                || x.contains("@inbox."));
    }

}
