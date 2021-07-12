package lt.rol.flightData;

public class Flight {

    private String date;
    private String flightDateFrom;
    private String flightDateTo;
    private String flightFrom;
    private String flightTo;
    private double priceWithoutTaxes;
    private double taxes;

    public Flight(String date, String flightDateFrom, String flightDateTo, String flightFrom, String flightTo,
                                                             double priceWithoutTaxes, double taxes) {
        this.date = date;
        this.flightDateFrom = flightDateFrom;
        this.flightDateTo = flightDateTo;
        this.flightFrom = flightFrom;
        this.flightTo = flightTo;
        this.priceWithoutTaxes = priceWithoutTaxes;
        this.taxes = taxes;
    }

    public String getFlightDateFrom() {
        return flightDateFrom;
    }

    public String getFlightDateTo() {
        return flightDateTo;
    }

    public String getFlightFrom() {
        return flightFrom;
    }

    public String getFlightTo() {
        return flightTo;
    }

    public double getPriceWithoutTaxes() {
        return priceWithoutTaxes;
    }

    public double getTaxes() {
        return taxes;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return getDate() + " " + getFlightDateFrom() + " " + getFlightDateTo()+ " " +
                getFlightFrom() + " " + getFlightTo() + " " +
                getPriceWithoutTaxes() + " " + getTaxes() + "\n";
    }
}
