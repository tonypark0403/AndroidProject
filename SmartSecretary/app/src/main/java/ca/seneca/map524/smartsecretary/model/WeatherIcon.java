package ca.seneca.map524.smartsecretary.model;

import android.widget.ImageView;

import ca.seneca.map524.smartsecretary.R;

/**
 * Created by Wonho on 11/12/2016.
 */
public class WeatherIcon {

    public static void setWeatherImage(ImageView view, int id) {
        switch (id) {
            case 200:
            case 201:
            case 202:
            case 230:
            case 231:
            case 232:
                view.setImageResource(R.drawable.wi_owm_200);
                break;
            case 210:
            case 211:
            case 212:
            case 221:
                view.setImageResource(R.drawable.wi_owm_210);
                break;
            case 300:
            case 301:
            case 321:
            case 500:
                view.setImageResource(R.drawable.wi_owm_300);
                break;
            case 302:
            case 311:
            case 312:
            case 314:
            case 501:
            case 502:
            case 503:
            case 504:
                view.setImageResource(R.drawable.wi_owm_302);
                break;
            case 310:
            case 511:
            case 611:
            case 612:
            case 615:
            case 616:
            case 620:
                view.setImageResource(R.drawable.wi_owm_310);
                break;
            case 313:
            case 520:
            case 521:
            case 522:
            case 701:
                view.setImageResource(R.drawable.wi_owm_313);
                break;
            case 531:
            case 901:
                view.setImageResource(R.drawable.wi_owm_531);
                break;
            case 600:
            case 601:
            case 621:
            case 622:
                view.setImageResource(R.drawable.wi_owm_600);
                break;
            case 602:
                view.setImageResource(R.drawable.wi_owm_602);
                break;
            case 711:
                view.setImageResource(R.drawable.wi_owm_711);
                break;
            case 721:
                view.setImageResource(R.drawable.wi_owm_721);
                break;
            case 731:
            case 761:
            case 762:
                view.setImageResource(R.drawable.wi_owm_731);
                break;
            case 741:
                view.setImageResource(R.drawable.wi_owm_741);
                break;
            case 771:
            case 801:
            case 802:
                view.setImageResource(R.drawable.wi_owm_771);
                break;
            case 781:
            case 900:
                view.setImageResource(R.drawable.wi_owm_781);
                break;
            case 803:
                view.setImageResource(R.drawable.wi_owm_803);
                break;
            case 804:
                view.setImageResource(R.drawable.wi_owm_804);
                break;
            case 902:
                view.setImageResource(R.drawable.wi_owm_902);
                break;
            case 903:
                view.setImageResource(R.drawable.wi_owm_903);
                break;
            case 904:
                view.setImageResource(R.drawable.wi_owm_904);
                break;
            case 905:
                view.setImageResource(R.drawable.wi_owm_905);
                break;
            case 906:
                view.setImageResource(R.drawable.wi_owm_906);
                break;
            case 957:
                view.setImageResource(R.drawable.wi_owm_957);
                break;
            case 800:
            default:
                view.setImageResource(R.drawable.wi_owm_800);
        }
    }

    public static void setForecastImage(ImageView view, int id) {
        switch (id) {
            case 200:
            case 201:
            case 202:
            case 230:
            case 231:
            case 232:
                view.setImageResource(R.drawable.wi_owm_200_orange);
                break;
            case 210:
            case 211:
            case 212:
            case 221:
                view.setImageResource(R.drawable.wi_owm_210_orange);
                break;
            case 300:
            case 301:
            case 321:
            case 500:
                view.setImageResource(R.drawable.wi_owm_300_orange);
                break;
            case 302:
            case 311:
            case 312:
            case 314:
            case 501:
            case 502:
            case 503:
            case 504:
                view.setImageResource(R.drawable.wi_owm_302_orange);
                break;
            case 310:
            case 511:
            case 611:
            case 612:
            case 615:
            case 616:
            case 620:
                view.setImageResource(R.drawable.wi_owm_310_orange);
                break;
            case 313:
            case 520:
            case 521:
            case 522:
            case 701:
                view.setImageResource(R.drawable.wi_owm_313_orange);
                break;
            case 531:
            case 901:
                view.setImageResource(R.drawable.wi_owm_531_orange);
                break;
            case 600:
            case 601:
            case 621:
            case 622:
                view.setImageResource(R.drawable.wi_owm_600_orange);
                break;
            case 602:
                view.setImageResource(R.drawable.wi_owm_602_orange);
                break;
            case 711:
                view.setImageResource(R.drawable.wi_owm_711_orange);
                break;
            case 721:
                view.setImageResource(R.drawable.wi_owm_721_orange);
                break;
            case 731:
            case 761:
            case 762:
                view.setImageResource(R.drawable.wi_owm_731_orange);
                break;
            case 741:
                view.setImageResource(R.drawable.wi_owm_741_orange);
                break;
            case 771:
            case 801:
            case 802:
                view.setImageResource(R.drawable.wi_owm_771_orange);
                break;
            case 781:
            case 900:
                view.setImageResource(R.drawable.wi_owm_781_orange);
                break;
            case 803:
                view.setImageResource(R.drawable.wi_owm_803_orange);
                break;
            case 804:
                view.setImageResource(R.drawable.wi_owm_804_orange);
                break;
            case 902:
                view.setImageResource(R.drawable.wi_owm_902_orange);
                break;
            case 903:
                view.setImageResource(R.drawable.wi_owm_903_orange);
                break;
            case 904:
                view.setImageResource(R.drawable.wi_owm_904_orange);
                break;
            case 905:
                view.setImageResource(R.drawable.wi_owm_905_orange);
                break;
            case 906:
                view.setImageResource(R.drawable.wi_owm_906_orange);
                break;
            case 957:
                view.setImageResource(R.drawable.wi_owm_957_orange);
                break;
            case 800:
            default:
                view.setImageResource(R.drawable.wi_owm_800_orange);
        }
    }
}
