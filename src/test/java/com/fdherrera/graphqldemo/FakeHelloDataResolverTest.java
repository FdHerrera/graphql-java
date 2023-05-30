package com.fdherrera.graphqldemo;

import com.fdherrera.graphqldemo.generated.types.Hello;
import com.netflix.graphql.dgs.DgsQueryExecutor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FakeHelloDataResolverTest {
    @Autowired private DgsQueryExecutor queryExecutor;

    @Test
    void shouldExecuteAndReturnNonNullValuesOnOneHello() {
        String query = """
        {
            oneHello {
                text 
                randomNumber
            }
        }
        """;

        String actualText = queryExecutor.executeAndExtractJsonPath(query, "data.oneHello.text");
        Integer actualRandomNumber = queryExecutor.executeAndExtractJsonPath(query, "data.oneHello.randomNumber");

        assertNotNull(actualText);
        assertNotNull(actualRandomNumber);
    }

    @Test
    public void shouldExecuteAndReturnNonNullValuesOnAllHellos() {
        String query = """
        {
            allHellos {
                text 
                randomNumber
            }
        }
        """;

        List<Hello> actualHellos = queryExecutor.executeAndExtractJsonPath(query, "data.allHellos");

        assertNotNull(actualHellos);
        assertEquals(20, actualHellos.size());
    }
}
