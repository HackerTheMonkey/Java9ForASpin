package com.jo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.AbstractMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class TuplesAndPairs {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    
    /**
     * Java offered few utilities to allow us to create a minimalistic version of a two elements Tuple or a Pair, this
     * is what we have prior to Java 9
     */

    /*
        SimpleImmutableEntry

        - Immutable key and value
        - Is serializable
        - accepts nulls for either the key or the value

        Another class that is provided by the JDK is for a mutable version of the Entry, i.e. SimpleEntry which
        is Serializable as well!
     */
    @Test
    public void the_value_of_a_SimpleImmutableEntry_key_can_not_be_changed(){
        // Then
        expectedException.expect(UnsupportedOperationException.class);

        // Given
        Map.Entry<String, Integer> entry = new AbstractMap.SimpleImmutableEntry<>("foo", 1);

        // When
        entry.setValue(2);
    }

    @Test
    public void SimpleImmutableEntry_allows_null_keys(){
        // Given
        Map.Entry<MyKey, MyValue> entry = new AbstractMap.SimpleImmutableEntry<>(null, new MyValue("value"));

        // When
        assertThat(entry.getKey(), is(nullValue()));

    }

    @Test
    public void SimpleImmutableEntry_allows_null_values(){
        // Given
        Map.Entry<MyKey, MyValue> entry = new AbstractMap.SimpleImmutableEntry<>(new MyKey(1), null);

        // When
        assertThat(entry.getValue(), is(nullValue()));

    }

    /**
     * And this is what we have from Java 9, an easy way to create an immutable Entry which is:
     *
     * - Not serializable
     * - It's keys and values must not be null!
     * - is immutable in the sense that once the value is set, it can not be changed!
     * - Not suitable for identity intensive operations, as per it's Javadoc
     */
    @Test
    public void the_value_is_immutable(){
        // Then
        expectedException.expect(UnsupportedOperationException.class);
        
        // Given
        Map.Entry<String, Integer> entry = Map.entry("foo", 1);

        // When
        entry.setValue(2);
    }

    @Test
    public void null_keys_are_not_permitted(){
        // Then
        expectedException.expect(NullPointerException.class);

        // Given
        Map.Entry<String, Integer> entry = Map.entry(null, 1);
    }

    @Test
    public void null_values_are_not_permitted(){
        // Then
        expectedException.expect(NullPointerException.class);

        // Given
        Map.Entry<String, Integer> entry = Map.entry("foo", null);
    }

    // I wish I could use Lombok, big deep sigh!
    private static class MyKey{
        private int foo;

        public MyKey(int foo) {
            this.foo = foo;
        }

        public void setFoo(int foo) {
            this.foo = foo;
        }

        public int getFoo() {
            return foo;
        }
    }

    private static class MyValue{
        private String bar;

        public MyValue(String bar) {
            this.bar = bar;
        }

        public String getBar() {
            return bar;
        }

        public void setBar(String bar) {
            this.bar = bar;
        }
    }

}
