public class ParcelDeliverySystem{
    private static ParcelDeliveryManager MANAGER = ParcelDeliveryManager.getInstance();
    private static InputValidation VALIDATION = InputValidation.getInstance();

    public void start() {
        boolean isRunning = true;

        while(isRunning){
            System.out.println("=======================================================");
            System.out.println("                 Parcel Delivery System                ");
            System.out.println("=======================================================");

            System.out.println("1. Add Parcel");
            System.out.println("2. View All Parcels");
            System.out.println("3. Change Delivery Service");
            System.out.println("4. Mark as Delivered");
            System.out.println("5. Exit");

            System.out.println("--------------------------------------------------------");
            int choice = VALIDATION.getValidatedIntegerMinMax("Please select an option (1-5): ", "Invalid input! Please enter a number between 1 and 5.", 1, 5);

            switch (choice){
                case 1:
                    addParcel();
                    break;
                case 2:
                    viewAllParcels();
                    break;
                case 3:
                    changeDeliveryService();
                    break;
                case 4:
                    markAsDelivered();
                    break;
                case 5:
                    isRunning = false;
                    System.out.println("Thank you for using the Parcel Delivery System!");
                    break;
            }
        }   
    }

    public void addParcel() {
        System.out.println("=======================================================");
        System.out.println("                     Add Parcel                        ");
        System.out.println("=======================================================");

        System.out.println("--------------------------------------------------------");
        System.out.println("Parcel Types:");
        System.out.println("1. Document");
        System.out.println("2. Box");
        System.out.println("3. Fragile");

        System.out.println("--------------------------------------------------------");

        int parcelType = promptParcelType();
        String trackingNumber = promptnewTrackingNumber();
        String receiverName = promptReceiverName();
        double weight = 0;
        

        int numberOfPages = 0;
        int length = 0;
        int width = 0;
        int height = 0;
        String handlingNote = "";

        Parcel parcel = null;
        switch (parcelType){
            case 1:
                weight = promptDocumentWeight();
                numberOfPages = promptNumPages();

                parcel = new Document(trackingNumber, receiverName, weight, null, numberOfPages);
                break;
                case 2:
                weight = promptBoxWeight();
                length = promptBoxLength();
                width = promptBoxWidth();
                height = promptBoxHeight();

                parcel = new Box(trackingNumber, receiverName, weight, null, length, width, height);
                break;

                case 3:
                weight = promptFragileBoxWeight();
                length = promptBoxLength();
                width = promptBoxWidth();
                height = promptBoxHeight();
                handlingNote = promptHandlingNote();

                parcel = new Fragile(trackingNumber, receiverName, weight, null, length, width, height, handlingNote);
                break;
        }

        System.out.println("--------------------------------------------------------");
        System.out.println("Delivery Services:");
        System.out.println("1. Standard");
        System.out.println("2. Express");
        System.out.println("3. Same Day");

        boolean validDeliveryService = false;
        
        DeliveryService deliveryService;
        double deliveryFee = 0;
        while(!validDeliveryService){
            int deliveryServiceChoice = promptDeliveryService();

            switch (deliveryServiceChoice){
                case 1:
                    deliveryService = new StandardDelivery();
                    MANAGER.updateDeliveryService(parcel, deliveryService);
                    deliveryFee = MANAGER.updateDeliveryFee(parcel, deliveryService);
                    MANAGER.addParcel(parcel);
                    System.out.printf("Parcel added successfully! Delivery fee: Php %.2f\n", deliveryFee);
                    validDeliveryService = true;
                    break;
                case 2:
                    deliveryService = new ExpressDelivery();
                    MANAGER.updateDeliveryService(parcel, deliveryService);
                    deliveryFee = MANAGER.updateDeliveryFee(parcel, deliveryService);
                    MANAGER.addParcel(parcel);
                    System.out.printf("Parcel added successfully! Delivery fee: Php %.2f\n", deliveryFee);
                    validDeliveryService = true;
                    break;
                case 3:
                    if(!MANAGER.checkSameDayDeliveryServiceAvailability(parcel)){
                        break;
                    }
                    deliveryService = new SameDayDelivery();
                    MANAGER.updateDeliveryService(parcel, deliveryService);
                    deliveryFee = MANAGER.updateDeliveryFee(parcel, deliveryService);
                    MANAGER.addParcel(parcel);
                    System.out.printf("Parcel added successfully! Delivery fee: Php %.2f\n", deliveryFee);
                    validDeliveryService = true;
                    break;
            }
        }

    }

    public void viewAllParcels() {
        if (MANAGER.parcelInventoryIsEmpty()){
            return;
        }

        System.out.println("======================================================================================================================================================================");
        System.out.println("                                                                            ALL PARCELS                                                                               ");
        System.out.println("======================================================================================================================================================================");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s %-15s %-30s %-30s %-20s %-33s %s\n", "Tracking Number", "Type", "Receiver", "Chargeable Weight", "Service", " Delivery Fee", "Status");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        for (Parcel parcel: MANAGER.getParcels()){
            System.out.printf("%-20s %-15s %-30s %-30s %-20s %-33s %s\n", parcel.getTrackingNumber(), parcel.getParcelType(), parcel.getReceiverName(), String.format("%.1f kg", parcel.getChargeableWeight()), parcel.getDeliveryService(), String.format(" P%.2f", parcel.getDeliveryFee()), parcel.getStatus());
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public void changeDeliveryService() {
        if (MANAGER.parcelInventoryIsEmpty()){
            return;
        }

        System.out.println("=======================================================");
        System.out.println("                Change Delivery Service                ");
        System.out.println("=======================================================");

        String trackingNumber = promptTrackingNumber();

        Parcel parcel = MANAGER.getParcelByTrackingNumber(trackingNumber);

        if(!MANAGER.checkIfParcelExists(parcel)){
            return;
        } 
        else if (MANAGER.checkIfParcelIsDelivered(parcel)){
            return;
        }

        System.out.println("--------------------------------------------------------");
        System.out.println("Select new Delivery Service:");
        System.out.println("1. Standard");
        System.out.println("2. Express");
        System.out.println("3. Same Day");

        int newParcelDeliveryServiceChoice = promptDeliveryService();
        DeliveryService newDeliveryService;
        double newDeliveryFee = 0;

        switch (newParcelDeliveryServiceChoice){
            case 1:
                newDeliveryService = new StandardDelivery();
                if (MANAGER.checkIfSameDeliveryService(parcel, newDeliveryService, "This parcel already has standard delivery service.")){
                    return;
                }
                MANAGER.updateDeliveryService(parcel, newDeliveryService);
                newDeliveryFee = MANAGER.updateDeliveryFee(parcel, newDeliveryService);
                System.out.printf("Delivery service changed! New fee: Php %.2f\n", newDeliveryFee);
                break;
            case 2:
                newDeliveryService = new ExpressDelivery();
                if (MANAGER.checkIfSameDeliveryService(parcel, newDeliveryService, "This parcel already has express delivery service.")){
                    return;
                }
                MANAGER.updateDeliveryService(parcel, newDeliveryService);
                newDeliveryFee = MANAGER.updateDeliveryFee(parcel, newDeliveryService);
                System.out.printf("Delivery service changed! New fee: Php %.2f\n", newDeliveryFee);
                break;
            case 3:
                if(!MANAGER.checkSameDayDeliveryServiceAvailability(parcel)){
                    return;
                }
                newDeliveryService = new SameDayDelivery();
                if (MANAGER.checkIfSameDeliveryService(parcel, newDeliveryService, "This parcel already has same day delivery service.")){
                    return;
                }
                MANAGER.updateDeliveryService(parcel, newDeliveryService);
                newDeliveryFee = MANAGER.updateDeliveryFee(parcel, newDeliveryService);
                System.out.printf("Delivery service changed! New fee: Php %.2f\n", newDeliveryFee);
                break;
        }
    }

    public void markAsDelivered() {
        if (MANAGER.parcelInventoryIsEmpty()){
            return;
        }

        System.out.println("=======================================================");
        System.out.println("                Mark Parcel as Delivered               ");
        System.out.println("=======================================================");

        String trackingNumber = promptTrackingNumber();
        Parcel parcel = MANAGER.getParcelByTrackingNumber(trackingNumber);

        if(!MANAGER.checkIfParcelExists(parcel)){
            return;
        } 
        else if (MANAGER.checkIfParcelIsDelivered(parcel)){
            return;
        }

        if (parcel.getStatus().equalsIgnoreCase("PENDING")){
            MANAGER.updateDeliveryStatus(parcel);
            System.out.println("Parcel delivered successfully!");
            return;
        }

    }

    //Prompt Methods
    public int promptParcelType(){
        return VALIDATION.getValidatedIntegerMinMax("Please select an option (1-3): ", "Invalid input! Please enter a number between 1 and 3.", 1, 3);
    }

    public String promptTrackingNumber(){
        return VALIDATION.getValidatedStringInput("Enter Parcel Tracking Number: ", "Tracking number cannot be empty and must be alphanumeric, \"ABC\" followed by 4 digits (e.g., ABC1234)", "(?i)^ABC\\d{4}$");
    }

    public String promptnewTrackingNumber(){
        String trackingNumber = ""; 
        boolean validTrackingNumber = false;

        while (!validTrackingNumber){
            trackingNumber = VALIDATION.getValidatedStringInput("Enter tracking number: ", "Tracking number cannot be empty and must be alphanumeric, \"ABC\" followed by 4 digits (e.g., ABC1234)", "(?i)^ABC\\d{4}$");

            if (MANAGER.isDuplicateParcelTrackingNumber(trackingNumber)){
                System.out.println("Duplicate Tracking Number! Please enter a unique tracking number.");
            }

            else{
                validTrackingNumber = true;
            }
        }
        return trackingNumber.toUpperCase();
    }

    public String promptReceiverName(){
        return VALIDATION.getValidatedStringInput("Enter receiver name: ", "Invalid input! Receiver name cannot be empty.", "^.+$");
    }

    public double promptDocumentWeight(){
        return VALIDATION.getValidatedDoubleMax("Enter weight (kg): ","Invalid input! Weight must be greater than 0 and not exceed 0.5 kg.", 0.5);
    }

    public double promptBoxWeight(){
        return VALIDATION.getValidatedDoubleMax("Enter weight (kg): ","Invalid input! Weight must be greater than 0 and not exceed 50 kg.", 50);
    }

    public double promptFragileBoxWeight(){
        return VALIDATION.getValidatedDoubleMax("Enter weight (kg): ","Invalid input! Weight must be greater than 0 and not exceed 30kg.", 30);
    }

    public int promptNumPages(){
        return VALIDATION.getValidatedIntegerMinMax("Enter number of pages (1-100): ", "Invalid input! Number of pages must be between 1 and 100.", 1, 100);
    }

    public int promptBoxLength(){
        return VALIDATION.getValidatedIntegerMinMax("Enter Box Length (Max. 150 cm): ", "Invalid input! Box length must be between 1 and 150 cm.", 1, 150);
    }

    public int promptBoxWidth(){
        return VALIDATION.getValidatedIntegerMinMax("Enter Box Width (Max. 150 cm): ", "Invalid input! Box width must be between 1 and 150 cm.", 1, 150);
    }

    public int promptBoxHeight(){
        return VALIDATION.getValidatedIntegerMinMax("Enter Box Height (Max. 150 cm): ", "Invalid input! Box height must be between 1 and 150 cm.", 1, 150);
    }

    public String promptHandlingNote(){
        return VALIDATION.getValidatedStringInput("Enter Handling note: ", "Invalid input! Handling note cannot be empty.", "^.+$");
    }

    public int promptDeliveryService(){
        return VALIDATION.getValidatedIntegerMinMax("Please select an option (1-3): ", "Invalid input! Please enter a number between 1 and 3.", 1, 3);
    }
}
