package nl.peterstreef.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = JacksonPrimitiveIntIssueApplication.class)
public class JacksonTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void readerForListOnRecordWithIntField() throws IOException {
        String json = "[{\"value\": 1},{\"value\": 2}]";
        List<PrimitiveRecord> records = objectMapper.readerForListOf(PrimitiveRecord.class).readValue(json);
        assertThat(records).hasSize(2);

        assertThat(records.get(0).value()).isEqualTo(1);
        assertThat(records.get(1).value()).isEqualTo(2);
    }

    @Test
    void readerForListOnRecordWithBoxedIntegerField() throws IOException {
        String json = "[{\"value\": 1},{\"value\": 2}]";
        List<BoxedRecord> records = objectMapper.readerForListOf(BoxedRecord.class).readValue(json);
        assertThat(records).hasSize(2);

        assertThat(records.get(0).value()).isEqualTo(1);
        assertThat(records.get(1).value()).isEqualTo(2);
    }

    @Test
    void readerForListOnRecordWithIntFieldCustomObjectMapper() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "[{\"value\": 1},{\"value\": 2}]";
        List<BoxedRecord> records = objectMapper.readerForListOf(BoxedRecord.class).readValue(json);
        assertThat(records).hasSize(2);

        assertThat(records.get(0).value()).isEqualTo(1);
        assertThat(records.get(1).value()).isEqualTo(2);
    }

    record PrimitiveRecord(int value) {
    }

    record BoxedRecord(Integer value) {
    }
}
