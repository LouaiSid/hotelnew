package hotel.classes;

import java.util.ArrayList;

/**
 *
 * @author Faysal Ahmed
 */
public class Booking {
    
       
    private UserInfo customer;
    ArrayList<Room> rooms;
    
    
    
    private int bookingId;
    private String checkInDateTime;
    private String checkOutDateTime;
    private String bookingType;
    private int person;
    
    
    
    
    public Booking()
    {
        customer = new UserInfo();
        rooms = new ArrayList<>();
        bookingId = -1;
        bookingType = "Reserved";
        
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

   
    
    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }
    
    
    
    
    public void addRoom(String roomNo)
    {
        rooms.add(new Room(roomNo));
        
    }
    
    public void removeRoom(String roomNo)
    {
        for(Room a: rooms)
        {
            if(a.getRoomNo().equals(roomNo))
            {
                rooms.remove(a);
            }
        }
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    // public ArrayList<Room> getRooms() {
    //     return rooms;
    // }
   
    
    public ArrayList<Room> getRooms() {
        return rooms;
    }
    
    // public int getRoomsFare() {
    //     // Ton code pour calculer ou retourner le tarif doit être ici
    //     return 0; // Valeur par défaut temporaire, à remplacer par ta logique réelle
    // }
    
    
    public int getRoomsFare()
    {
        int total = 0;
        for(Room room:rooms)
        {
            total += room.getRoomClass().getPricePerDay();
        }
        return total;
    }

    public UserInfo getCustomer() {
        return customer;
    }

    public void setCustomer(UserInfo customer) {
        this.customer = customer;
    }

    

    public void setCheckOutDateTime(String checkOutDateTime) {
        this.checkOutDateTime = checkOutDateTime;
    }

    public String getCheckInDateTime() {
        return checkInDateTime;
    }

    public void setCheckInDateTime(String checkInDateTime) {
        this.checkInDateTime = checkInDateTime;
    }

    public String getCheckOutDateTime() {
        return checkOutDateTime;
    }



 
    
    
    
            
    
}
