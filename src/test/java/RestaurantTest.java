import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest { 
        Restaurant restaurant;
        //REFACTOR ALL THE REPEATED LINES OF CODE
        private void createRestaurantInfo() {
            LocalTime openingTime = LocalTime.parse("10:30:00");
            LocalTime closingTime = LocalTime.parse("22:00:00");
            restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
            restaurant.addToMenu("Sweet corn soup", 119);
            restaurant.addToMenu("Vegetable lasagne", 269);
        }
        //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        createRestaurantInfo();
        LocalTime nowIsOpeningTime= LocalTime.parse("10:30:01");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(nowIsOpeningTime);
        assertTrue(spiedRestaurant.isRestaurantOpen());
        //WRITE UNIT TEST CASE HERE
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        createRestaurantInfo();
        LocalTime nowIsOpeningTime= LocalTime.parse("08:00:00");
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(nowIsOpeningTime);
        assertFalse(spiedRestaurant.isRestaurantOpen());
        //WRITE UNIT TEST CASE HERE
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        createRestaurantInfo();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        createRestaurantInfo();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        createRestaurantInfo();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>ORDER<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void chose_one_item_in_menu_should_return_expected_order_cost_of_that_item_only() {
        createRestaurantInfo();
        int orderTotalCost=restaurant.getOrderTotal("Sweet corn soup");
        assertEquals(restaurant.findItemByName("Sweet corn soup").getPrice(),orderTotalCost);
        System.out.println("Your order will cost $"+orderTotalCost);
    }
    @Test
    public void chose_two_items_in_menu_should_return_expected_order_total_cost_of_two_items() {
        createRestaurantInfo();
        int orderTotalCost=restaurant.getOrderTotal("Sweet corn soup","Vegetable lasagne");
        int firstItemprice=restaurant.findItemByName("Sweet corn soup").getPrice();
        int secondItemprice=restaurant.findItemByName("Vegetable lasagne").getPrice();
        assertEquals(firstItemprice+secondItemprice,orderTotalCost);
        System.out.println("Your order will cost $"+orderTotalCost);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<ORDER>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}