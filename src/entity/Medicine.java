package entity;

//pharmarcy module
public class Medicine implements Comparable<Medicine> {
    private String medicineId;
    private String name;
    private String brand;
    private int stockQuantity;
    private String expiryDate;
    private double price;
    private String purpose; //pain relief, antibiotic
    private String activeIngredient; //paracetamol, amoxicillin
    private String category; 

    public Medicine() {
        this("", "", "", 0, "", 0.0, "", "", "");
    }

    public Medicine(String medicineId, String name, String brand, int stockQuantity, String expiryDate, double price, String purpose, String activeIngredient, String category) {
        this.medicineId = medicineId;
        this.name = name;
        this.brand = brand;
        this.stockQuantity = stockQuantity;
        this.expiryDate = expiryDate;
        this.price = price;
        this.purpose = purpose;
        this.activeIngredient = activeIngredient;
        this.category = category;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "medicineId='" + medicineId + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", stockQuantity=" + stockQuantity +
                ", expiryDate='" + expiryDate + '\'' +
                ", price=" + price +
                ", purpose='" + purpose + '\'' +
                ", activeIngredient='" + activeIngredient + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Medicine other = (Medicine) obj;
        if (!java.util.Objects.equals(this.medicineId, other.medicineId)) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Medicine other) {
        return this.medicineId.compareTo(other.medicineId);
    }
}
