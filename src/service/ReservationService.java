package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationService {

    private static ReservationService instance;
    private List<Reservation> reservations;
    private List<IRoom> rooms;

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
        rooms.add(room);
    }

    public IRoom getRoom(String roomNumber) {
        for (IRoom room : rooms) {
            if (room.getRoomNumber().equals(roomNumber)) {
                return room;
            }
        }
        return null;
    }

    public List<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> bookedRooms = new ArrayList<>();
        List<IRoom> unbookedRooms = new ArrayList<>();

        for (Reservation reservation : reservations) {

            //Find rooms which are booked within the given dates
            if (reservation.getCheckInDate().equals(checkInDate) &&
            reservation.getCheckOutDate().equals(checkInDate)) {
                bookedRooms.add(reservation.getRoom());
                continue;
            }
            if (reservation.getCheckInDate().after(checkInDate) &&
            reservation.getCheckOutDate().before(checkOutDate)) {
                bookedRooms.add(reservation.getRoom());
            }
        }

        //Filter unbooked rooms using list of booked rooms
        for (IRoom room : rooms) {
            if (!bookedRooms.contains(room)) {
                unbookedRooms.add(room);
            }
        }
        return unbookedRooms;
    }

    public Reservation reserveRoom(Customer customer, IRoom room,
                                   Date checkInDate, Date checkOutDate) {
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
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }
}
