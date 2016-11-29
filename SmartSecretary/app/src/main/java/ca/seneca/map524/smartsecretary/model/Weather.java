package ca.seneca.map524.smartsecretary.model;

/**
 * This is a tutorial source code
 * provided "as is" and without warranties.
 *
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 *
 * or write an email to
 * survivingwithandroid@gmail.com
 *
 */

/*
 * Copyright (C) 2013 Surviving with Android (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class Weather {

    //public Location location;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow();
    public Clouds clouds = new Clouds();
    public DateTime dateTime = new DateTime();

    public byte[] iconData;

    public class DateTime {
        private String dateTime;

        public String getDateTime() { return dateTime; }
        public void setDateTime(String time) { this.dateTime = time; }
    }

    public  class CurrentCondition {
        private int weatherId;
        private String condition;
        private String descr;
        private String icon;


        private double pressure;
        private double humidity;

        public int getWeatherId() {
            return weatherId;
        }
        public void setWeatherId(int weatherId) {
            this.weatherId = weatherId;
        }
        public String getCondition() {
            return condition;
        }
        public void setCondition(String condition) {
            this.condition = condition;
        }
        public String getDescr() {
            return descr;
        }
        public void setDescr(String descr) {
            this.descr = descr;
        }
        public String getIcon() {
            return icon;
        }
        public void setIcon(String icon) {
            this.icon = icon;
        }
        public double getPressure() {
            return pressure;
        }
        public void setPressure(double pressure) {
            this.pressure = pressure;
        }
        public double getHumidity() {
            return humidity;
        }
        public void setHumidity(double humidity) {
            this.humidity = humidity;
        }
    }

    public  class Temperature {
        private double temp;
        private double minTemp;
        private double maxTemp;

        public double getTemp() {
            return temp;
        }
        public void setTemp(double temp) {
            this.temp = temp;
        }
        public double getMinTemp() {
            return minTemp;
        }
        public void setMinTemp(double minTemp) {
            this.minTemp = minTemp;
        }
        public double getMaxTemp() {
            return maxTemp;
        }
        public void setMaxTemp(double maxTemp) {
            this.maxTemp = maxTemp;
        }
    }

    public  class Wind {
        private double speed;
        private double deg;
        public double getSpeed() {
            return speed;
        }
        public void setSpeed(double speed) {
            this.speed = speed;
        }
        public double getDeg() {
            return deg;
        }
        public void setDeg(double deg) {
            this.deg = deg;
        }
    }

    public  class Rain {
        private String time;
        private double ammount;
        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public double getAmmount() {
            return ammount;
        }
        public void setAmmount(double ammount) {
            this.ammount = ammount;
        }
    }

    public  class Snow {
        private String time;
        private double ammount;

        public String getTime() {
            return time;
        }
        public void setTime(String time) {
            this.time = time;
        }
        public double getAmmount() {
            return ammount;
        }
        public void setAmmount(double ammount) {
            this.ammount = ammount;
        }
    }

    public  class Clouds {
        private int perc;

        public int getPerc() {
            return perc;
        }

        public void setPerc(int perc) {
            this.perc = perc;
        }
    }
}