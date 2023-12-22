public class Person {
    private int rank;
    private String name;
    private double networth;
    private int age;
    private String country;
    private String source;
    private String industry;

    public Person(int rank, String name, double networth, int age, 
                    String country, String source, String industry) {
        this.rank = rank;
        this.name = name;
        this.networth = networth;
        this.age = age;
        this.country = country;
        this.source = source;
        this.industry = industry;
    }

    @Override
    public String toString() {
        return String.format("Rank %s,Name %s,Networth %s,Age %s,Country %s,Source %s,Industry %s",
                this.rank, this.name, this.networth, this.age, this.country, this.source, this.industry);
    }
}
