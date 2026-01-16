package DesignPattern.StructuralDesign.flyweightPattern;

public class Sprite {
    
    private String image;
    private String color;
    
    public Sprite() {
        // Sprite initialization
        this.image = "humanoid_sprite.png";
        this.color = "silver";
    }
    
    public String getImage() {
        return image;
    }
    
    public void setImage(String image) {
        this.image = image;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
    
    @Override
    public String toString() {
        return "Sprite [image=" + image + ", color=" + color + "]";
    }   
}