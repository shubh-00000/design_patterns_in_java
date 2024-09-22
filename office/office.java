package office;
import java.util.*;
import java.util.concurrent.*;

// Singleton Pattern: OfficeFacility to handle all configurations
class OfficeFacility {
    private static OfficeFacility instance;
    private List<MeetingRoom> rooms;
    private Map<String, String> bookings; 
    private int roomCount;

    private OfficeFacility() {
        rooms = new ArrayList<>();
        bookings = new HashMap<>();
    }

    public static synchronized OfficeFacility getInstance() {
        if (instance == null) {
            instance = new OfficeFacility();
        }
        return instance;
    }

    public void configureOffice(int roomCount) {
        this.roomCount = roomCount;
        for (int i = 0; i < roomCount; i++) {
            rooms.add(new MeetingRoom("Room-" + (i + 1)));
        }
        System.out.println("Office configured with " + roomCount + " meeting rooms.`");
    }

    public List<MeetingRoom> getRooms() {
        return rooms;
    }

    public void setRoomCapacity(int roomIndex, int capacity) {
        if (roomIndex < 1 || roomIndex > roomCount) {
            System.out.println("Invalid room number. Please enter a valid room number.");
        } else if (capacity <= 0) {
            System.out.println("Invalid capacity. Please enter a valid positive number.");
        } else {
            rooms.get(roomIndex - 1).setMaxCapacity(capacity);
            System.out.println("Room " + roomIndex + " maximum capacity set to " + capacity + ".");
        }
    }

    public void addOccupants(int roomIndex, int occupants) {
        if (roomIndex < 1 || roomIndex > roomCount) {
            System.out.println("Room " + roomIndex + " does not exist.");
        } else {
            MeetingRoom room = rooms.get(roomIndex - 1);
            if (occupants >= 2) {
                room.setOccupancy(occupants);
                System.out.println("Room " + roomIndex + " is now occupied by " + occupants + " persons. AC and lights turned on.");
            } else if (occupants == 0) {
                room.leaveRoom();
                System.out.println("Room " + roomIndex + " is now unoccupied. AC and lights turned off.");
            } else {
                System.out.println("Room " + roomIndex + " occupancy insufficient to mark as occupied.");
            }
        }
    }

    public String blockRoom(int roomIndex, String startTime, int duration) {
        if (roomIndex < 1 || roomIndex > roomCount) {
            return "Invalid room number. Please enter a valid room number.";
        }
        MeetingRoom room = rooms.get(roomIndex - 1);
        if (room.isBooked()) {
            return "Room " + roomIndex + " is already booked during this time. Cannot book.";
        }
        room.bookRoom();
        return "Room " + roomIndex + " booked from " + startTime + " for " + duration + " minutes.";
    }

    public String cancelBooking(int roomIndex) {
        if (roomIndex < 1 || roomIndex > roomCount) {
            return "Invalid room number. Please enter a valid room number.";
        }
        MeetingRoom room = rooms.get(roomIndex - 1);
        if (!room.isBooked()) {
            return "Room " + roomIndex + " is not booked. Cannot cancel booking.";
        }
        room.cancelRoom();
        return "Booking for Room " + roomIndex + " cancelled successfully.";
    }

    public void handleAutoRelease() {
        rooms.forEach(MeetingRoom::checkAutoRelease);
    }
}

// Observer Pattern: MeetingRoom has sensors (occupancy, AC, lights)
class MeetingRoom {
    private String name;
    private boolean isBooked;
    private boolean isOccupied;
    private int maxCapacity;
    private int currentOccupants;
    private ScheduledFuture<?> autoReleaseTask;
    private final List<Observer> observers;

    public MeetingRoom(String name) {
        this.name = name;
        this.observers = new ArrayList<>();
        this.isBooked = false;
        this.isOccupied = false;
        this.maxCapacity = 0;
        this.currentOccupants = 0;
    }

    public void setMaxCapacity(int capacity) {
        this.maxCapacity = capacity;
    }

    public void setOccupancy(int occupants) {
        if (occupants >= 2) {
            isOccupied = true;
            currentOccupants = occupants;
            notifyObservers("occupied");
            cancelAutoReleaseTask();
        } else {
            leaveRoom();
        }
    }

    public void leaveRoom() {
        isOccupied = false;
        currentOccupants = 0;
        notifyObservers("vacant");
        scheduleAutoRelease();
    }

    public boolean bookRoom() {
        if (!isBooked) {
            isBooked = true;
            notifyObservers("booked");
            return true;
        }
        return false;
    }

    public void cancelRoom() {
        isBooked = false;
        notifyObservers("canceled");
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void checkAutoRelease() {
        if (isBooked && !isOccupied && autoReleaseTask == null) {
            autoReleaseTask = scheduleAutoRelease();
        }
    }

    private ScheduledFuture<?> scheduleAutoRelease() {
        return Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            if (!isOccupied) {
                System.out.println(name + " is now unoccupied. Booking released. AC and lights off.");
                cancelRoom();
            }
        }, 5, TimeUnit.MINUTES);
    }

    private void cancelAutoReleaseTask() {
        if (autoReleaseTask != null) {
            autoReleaseTask.cancel(true);
            autoReleaseTask = null;
        }
    }

    public void addObserver(Observer o) {
        observers.add(o);
    }

    private void notifyObservers(String status) {
        for (Observer o : observers) {
            o.update(name, status);
        }
    }
}

// Observer Pattern: Light and AC observing occupancy status
interface Observer {
    void update(String roomName, String status);
}

class Light implements Observer {
    @Override
    public void update(String roomName, String status) {
        if (status.equals("occupied")) {
            System.out.println("Lights ON in " + roomName);
        } else {
            System.out.println("Lights OFF in " + roomName);
        }
    }
}

class AC implements Observer {
    @Override
    public void update(String roomName, String status) {
        if (status.equals("occupied")) {
            System.out.println("AC ON in " + roomName);
        } else {
            System.out.println("AC OFF in " + roomName);
        }
    }
}

// Main Application
public class office {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OfficeFacility office = OfficeFacility.getInstance();

        System.out.print("Enter number of meeting rooms: ");
        int roomCount = scanner.nextInt();
        office.configureOffice(roomCount);

        office.getRooms().forEach(room -> {
            room.addObserver(new Light());
            room.addObserver(new AC());
        });

        System.out.println("Welcome to Smart Office Facility System!");
        while (true) {
            System.out.println("1. Set Room Capacity\n2. Add Occupants\n3. Block (Book) Room\n4. Cancel Booking\n5. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter room number: ");
                    int roomNumber = scanner.nextInt();
                    System.out.print("Enter room max capacity: ");
                    int capacity = scanner.nextInt();
                    office.setRoomCapacity(roomNumber, capacity);
                    break;

                case 2:
                    System.out.print("Enter room number: ");
                    roomNumber = scanner.nextInt();
                    System.out.print("Enter number of occupants: ");
                    int occupants = scanner.nextInt();
                    office.addOccupants(roomNumber, occupants);
                    break;

                case 3:
                    System.out.print("Enter room number: ");
                    roomNumber = scanner.nextInt();
                    System.out.print("Enter booking start time (HH:mm): ");
                    String startTime = scanner.next();
                    System.out.print("Enter duration (minutes): ");
                    int duration = scanner.nextInt();
                    System.out.println(office.blockRoom(roomNumber, startTime, duration));
                    break;

                case 4:
                    System.out.print("Enter room number: ");
                    roomNumber = scanner.nextInt();
                    System.out.println(office.cancelBooking(roomNumber));
                    break;

                case 5:
                    System.out.println("Exiting system...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }

            office.handleAutoRelease(); // Simulate the auto-release feature
        }
    }
}
