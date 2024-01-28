package pack.sor.carnote.model;

import android.support.annotation.DrawableRes;

import java.util.Date;

/**
 * Interfejs zapewniający funkcjonalność pobrania
 */

public interface ViewHolderAdaptable
{
    Date getDate();

    Integer getMileage();

    String getFirstText();

    String getSecondText();

    @DrawableRes
    Integer getCategoryDrawable();
}
