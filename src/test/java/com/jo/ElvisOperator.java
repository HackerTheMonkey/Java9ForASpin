package com.jo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ElvisOperator {
    /**
     * In Groovy, we have the following syntax for an Elvis operator
     * <p>
     * name = person.name ?: "John Doe"
     * <p>
     * This is an attempt to 'effectively' achieve the same thing in Java 9
     */

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * Eager evaluation of the default value
     */
    @Test
    public void what_effectively_count_as_an_elvis_operator_eager_computation_of_default_value(){
        assertThat(Objects.requireNonNullElse(new Person(null).getName(), "John Doe"), is(notNullValue()));
    }

    @Test
    public void throws_a_NPE_if_the_default_value_is_null_as_well_eager_scenario(){
        // Then
        expectedException.expect(NullPointerException.class);

        // When
        Objects.requireNonNullElse(new Person(null).getName(), null);
    }



    /**
     * Lazy evaluation of the default value
     */
    @Test
    public void what_effectively_count_as_an_elvis_operator_lazy_computation_of_default_value(){
        assertThat(Objects.requireNonNullElseGet(new Person(null).getName(), () -> "John Dao"), is(notNullValue()));
    }

    @Test
    public void throws_a_NPE_if_the_default_value_is_null_as_well_lazy_scenario(){
        // Then
        expectedException.expect(NullPointerException.class);

        // When
        Objects.requireNonNullElseGet(new Person(null).getName(), () -> null);
    }


    private static class Person{

        private final String name;
        private Integer age;

        private Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Optional<Integer> getAge() {
            return Optional.ofNullable(age);
        }
    }
}
