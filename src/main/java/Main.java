import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        Producer<String, String> producer = createProducer(); //jest bezpieczny wielowatkowo i mozna uderzac do niego z wielu watkow
        ProducerRecord<String, String> record = new ProducerRecord<>("producer-example", "12345", "Test value");
        try {


//            producer.send(record); -->fire and forget- Najszybsze. Przydatne przy logach gdy coś tam straciliśmy i nie obchodzi nas to zbytnio by wstrzsymywac prace kafki

//            producer.send(record).get(); -->fire and wait ze ta wiaomosc dotarla. CZekamy synbchronicznie na odp. Nie wyśle kolejnej gdy nie przyszla odpowiedz. Zaiwrera funkcje o szczegółach zapisu danych
            producer.send(record).get();
            //lub można też przetworzyc za pomoca callback -działa asynchronicznie. Kafka nie czeka az dostaniemy odpowiedz od borkera tylko caly czas wysyla

//            producer.send(record, new Callback() {
//                @Override
//                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
//                    System.out.println("Poszło" + recordMetadata);
//                }
//            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Sent");
    }

    public static Producer<String, String> createProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "ZCMjs");

        return new KafkaProducer<>(properties);
    }

}
