package com.jo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.emptySet;
import static java.util.Map.entry;
import static java.util.Map.ofEntries;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CollectionLiterals {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void create_an_empty_list() {
        List<Integer> emptyList = List.of();
        assertThat(emptyList, is(emptyList()));
    }

    @Test
    public void create_a_list_with_few_elements(){
        List<String> listOfTwoElements = List.of("foo", "bar");

        assertThat(listOfTwoElements, hasItem("foo"));
        assertThat(listOfTwoElements, hasItem("bar"));
    }

    @Test
    public void create_a_list_of_an_array(){
        String[] someArray = {"1", "2"};
        List<String[]> listOfArrays = List.<String[]>of(someArray);

        assertThat(listOfArrays.size(), is(1));
    }

    @Test
    public void create_an_empty_set(){
        Set<Object> emptySet = Set.of();
        assertThat(emptySet, is(emptySet()));
    }

    @Test
    public void create_a_set_of_things(){
        Set<String> setOfThings = Set.of("foo", "bar");
        assertThat(setOfThings.size(), is(2));
    }

    @Test
    public void create_a_set_of_arrays(){
        // Given
        String[] array = {"1", "2"};

        // When
        Set<String[]> setOfStringArrays = Set.<String[]>of(array);

        // Then
        assertThat(setOfStringArrays.size(), is(1));
    }

    @Test
    public void create_an_empty_map(){
        Map<Object, Object> map = Map.of();
        assertThat(map, is(emptyMap()));
    }

    @Test
    public void create_a_map_of_things_using_the_OF_factory_method(){
        Map<String, String> map = Map.of("fooKey", "fooValue", "barKey", "barValue");

        assertThat(map.get("fooKey"), is("fooValue"));
        assertThat(map.get("barKey"), is("barValue"));
    }

    // This allows us to create an unlimited number of entries while the Map.of()
    // can only do so up to 10 entries!
    @Test
    public void create_a_map_of_things_using_the_entries_factory_method(){
        Map<String, Integer> map = ofEntries(
                entry("Jan", 1),
                entry("Feb", 2),
                entry("Mar", 3),
                entry("Apr", 4)
        );

        assertThat(map.get("Jan"), is(1));
        assertThat(map.get("Apr"), is(4));
    }


    /**
     * So that we can justify the addition of 11 of(...) factory methods by the Java designers.
     *
     * The performance of it does not seem to make any difference between the 3 methods, perhaps there has been
     * some sort of optimisation somewhere in Java 8 and Java 9
     */
    @Test
    public void compare_passing_arguments_to_a_method_against_passing_a_varargs(){

        int iterationsCount = 1000000000;

        String e1 = new String("e1");
        String e2 = new String("e2");
        String e3 = new String("e3");
        String e4 = new String("e4");
        String e5 = new String("e5");

        // call the zeroArgumentsMethod()
        long startTime = System.currentTimeMillis();
        for(int i = 0 ; i <= iterationsCount; i++){
            zeroArgumentsMethod();
        }
        System.out.print("zeroArgumentsMethod(): ");
        System.out.println(System.currentTimeMillis() - startTime);
                // call the multipleArgumentsMethod()
        startTime = System.currentTimeMillis();
        for(int i = 0 ; i <= iterationsCount; i++){
            multipleArgumentsMethod(e1, e2, e3, e4, e5);
        }
        System.out.print("multipleArgumentsMethod(): ");
        System.out.println(System.currentTimeMillis() - startTime);

        // call the methodWithVarargsParameter()
        startTime = System.currentTimeMillis();
        for(int i = 0 ; i <= iterationsCount; i++){
            methodWithVarargsParameter(e1, e2, e3, e4, e5);
        }
        System.out.print("methodWithVarargsParameter(): ");
        System.out.println(System.currentTimeMillis() - startTime);

        System.out.println("----------------------------------------------");
    }


    /**
     * Difference between a couple of ways with which we create lists
     * - Arrays.asList(...)
     * - List.of(...) (this one has been newly added to Java9)
     */

    @Test
    public void lists_created_via_Arrays_asList_can_not_be_resized(){
        // as the returned list is of a fixed size, we can't change it's size by adding a new element to it
        // Then
        expectedException.expect(UnsupportedOperationException.class);

        // Given
        String[] anArrayOfStrings = {"Zsh", "Ruby", "Scala"};
        List<String> list = Arrays.asList(anArrayOfStrings);

        // When
        list.add("something");
    }

    @Test
    public void lists_created_via_Arrays_asList_can_be_sorted(){

        /**
         * So in essence, the list is immutable in the sense that no new items can be added/removed to/from it, in other
         * words, it's size can not be changed. However, the order of it's internal elements can be modified so it's not
         * immutable from that aspect. We can think of this as being partially immutable!
         */

        // Given
        String[] anArrayOfStrings = {"Zsh", "Ruby", "Scala"};
        List<String> list = Arrays.asList(anArrayOfStrings);
        Collections.sort(list);

        // When
        assertThat(list, is(Arrays.asList("Ruby", "Scala", "Zsh")));
    }
    
    @Test
    public void lists_created_with_List_of_can_not_have_new_elements_added_to_it_due_to_its_immutability(){
        // Then
        expectedException.expect(UnsupportedOperationException.class);

        // Given
        String[] anArrayOfStrings = {"Zsh", "Ruby", "Scala"};
        List<String> list = List.of(anArrayOfStrings);

        // When
        list.add("something");
    }

    @Test
    public void lists_created_with_List_of_can_not_be_sorted(){
        /**
         * Because the returned list is immutable and the List.sort()
         * default method has been overridden to throw a UOE!
         */

        // Then
        expectedException.expect(UnsupportedOperationException.class);

        // Given
        String[] anArrayOfStrings = {"Zsh", "Ruby", "Scala"};
        List<String> list = List.of(anArrayOfStrings);

        // When
        Collections.sort(list);
    }

    /**
     * Some helper methods
     */
    private void zeroArgumentsMethod(){

    }

    private void multipleArgumentsMethod(String e1, String e2, String e3, String e4, String e5){

    }

    private void methodWithVarargsParameter(String... essss){

    }

}
