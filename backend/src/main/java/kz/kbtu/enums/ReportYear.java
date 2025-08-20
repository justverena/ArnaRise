package kz.kbtu.enums;

public enum ReportYear {
    Y2015(2015),
    Y2016(2016),
    Y2017(2017),
    Y2018(2018),
    Y2019(2019),
    Y2020(2020),
    Y2021(2021),
    Y2022(2022),
    Y2023(2023),
    Y2024(2024),
    Y2025(2025);

    private final int value;

    ReportYear(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ReportYear fromValue(int year) {
        for (ReportYear y : values()) {
            if (y.getValue() == year) {
                return y;
            }
        }
        throw new IllegalArgumentException("Unknown year: " + year);
    }
}
