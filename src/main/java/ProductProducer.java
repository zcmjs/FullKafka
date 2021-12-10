import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ProductProducer {

    public static void main(String[] args) {
        Producer<String, Product> producer = createProducer(); //jest bezpieczny wielowatkowo i mozna uderzac do niego z wielu watkow
        Product product = new Product("Siercepart33", 2137.69);
        ProducerRecord<String, Product> record = new ProducerRecord<>("producer-example", "kanister", product);
        try {
            producer.send(record).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Sent");
    }

    public static Producer<String, Product> createProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MyOwnJsonSerializer.class);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "ZCMjs");
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, MyOwnPartitioner.class);

        return new KafkaProducer<>(properties);
    }

}
