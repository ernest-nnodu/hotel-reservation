package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationService {

    private static ReservationService instance;
    private final List<Reservation> reservations;
    private final List<IRoom> rooms;

    private ReservationService() {
        rooms = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public void addRoom(IRoom room) {
        if (roomNumberExists(room.getRoomNumber())) {
            throw new IllegalArgumentException("Room number already exist!");
        }
        rooms.add(room);
    }

    private boolean roomNumberExists(String roomNumber) {

        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return true;
            }
        }
        return false;
    }

    public IRoom getRoom(String roomNumber) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    public List<IRoom> getAllRooms() {
        return rooms;
    }

    public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> bookedRooms = new ArrayList<>();
        List<IRoom> availableRooms = new ArrayList<>();

        //Find rooms which are booked within the given dates
        for (Reservation reservation : reservations) {
            if (!((checkOutDate.before(reservation.getCheckInDate())) ||
                    (checkInDate.after(reservation.getCheckOutDate())))) {
                bookedRooms.add(reservation.getRoom());
            }
        }

        //Filter available rooms from listed of booked rooms
        for (IRoom room : rooms) {
            if (!bookedRooms.contains(room)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Reservation reserveRoom(Customer customer, IRoom room,
                                   Date checkInDate, Date checkOutDate) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }

        if (checkOutDate.before(checkInDate)) {
            throw new IllegalArgumentException("Checkout date should be after Checkin date!");
        }

        if (room == null) {
            throw new IllegalArgumentException("Room number do not exist!");
        }

        List<IRoom> availableRooms = findRooms(checkInDate, checkOutDate);
        if (!availableRooms.contains(room)) {
            throw new IllegalArgumentException("Room not available!");
        }

        Reservation reservationToSave = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(reservationToSave);
        return reservationToSave;
    }

    public List<Reservation> getCustomerReservations(Customer customer) {
        List<Reservation> customersReservation = new ArrayList<>();

        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customersReservation.add(reservation);
            }
        }
        return customersReservation;
    }

    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations made!");
        }

        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
