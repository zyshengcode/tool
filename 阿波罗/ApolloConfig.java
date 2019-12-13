

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;

import java.util.HashMap;
import java.util.Map;

public class ApolloConfig {

    private Config config = null;

    private Map<String, String> cmcsmpostMap = new HashMap<>(20);

    private Map<String, String> cmcsmMap = new HashMap<>(20);

    public ApolloConfig() {
    }

    public ApolloConfig(String envStr) {
        this.config = ConfigService.getConfig(envStr + ".yml");
    }

    public Config getConfig() {
        return config;
    }

    private void setConfig(Config config) {
        this.config = config;
    }

    public String getValue(String key) {
        return this.config.getProperty(key, "");
    }

    public String[] getArray(String key) {
        return this.config.getArrayProperty(key, ",", null);
    }

    public Integer getIntValue(String key) {
        return this.config.getIntProperty(key, null);
    }

    public Map<String, String> getCmcsmpostMap() {

        this.cmcsmpostMap.put("appName", getValue("application.cmcsmpost.appName"));
        this.cmcsmpostMap.put("offsetTable", getValue("application.cmcsmpost.offsetTable"));

        this.cmcsmpostMap.put("driverName", getValue("application.cmcsmpost.jdbc.driverName"));
        this.cmcsmpostMap.put("url", getValue("application.cmcsmpost.jdbc.url"));
        this.cmcsmpostMap.put("username", getValue("application.cmcsmpost.jdbc.username"));
        this.cmcsmpostMap.put("password", getValue("application.cmcsmpost.jdbc.password"));
        this.cmcsmpostMap.put("fetchsize", getValue("application.cmcsmpost.jdbc.fetchsize"));

        this.cmcsmpostMap.put("duration", getValue("application.cmcsmpost.spark.duration"));
        this.cmcsmpostMap.put("stopGrace", getValue("application.cmcsmpost.spark.streaming.stopGracefullyOnShutdown"));
        this.cmcsmpostMap.put("networkTimeout", getValue("application.cmcsmpost.spark.network.timeout"));
        this.cmcsmpostMap.put("rpcAskTimeout", getValue("application.cmcsmpost.spark.rpc.askTimeout"));
        this.cmcsmpostMap.put("rpcNumRetries", getValue("application.cmcsmpost.spark.rpc.numRetries"));
        this.cmcsmpostMap.put("serializer", getValue("application.cmcsmpost.spark.serializer"));
        this.cmcsmpostMap.put("kryoRegistrator", getValue("application.cmcsmpost.spark.kryo.registrator"));

        this.cmcsmpostMap.put("topic", getValue("application.cmcsmpost.kafka.topic"));
        this.cmcsmpostMap.put("partitionSize", getValue("application.cmcsmpost.kafka.partition.size"));
        this.cmcsmpostMap.put("bootstrapServers", getValue("application.cmcsmpost.kafka.bootstrap.servers"));
        this.cmcsmpostMap.put("keyDeserializer", getValue("application.cmcsmpost.kafka.key.deserializer"));
        this.cmcsmpostMap.put("valueDeserializer", getValue("application.cmcsmpost.kafka.value.deserializer"));
        this.cmcsmpostMap.put("groupId", getValue("application.cmcsmpost.kafka.group.id"));
        this.cmcsmpostMap.put("autoOffsetReset", getValue("application.cmcsmpost.kafka.auto.offset.reset"));
        this.cmcsmpostMap.put("enableAutoCommit", getValue("application.cmcsmpost.kafka.enable.auto.commit"));
        this.cmcsmpostMap.put("maxPartitionFetchBytes", getValue("application.cmcsmpost.kafka.max.partition.fetch.bytes"));

        return cmcsmpostMap;
    }

    private void setCmcsmpostMap(Map<String, String> cmcsmpostMap) {
        this.cmcsmpostMap = cmcsmpostMap;
    }

    public Map<String, String> getCmcsmMap() {

        this.cmcsmMap.put("appName", getValue("application.cmcsm.appName"));
        this.cmcsmMap.put("tablePath", getValue("application.cmcsm.tablePath"));
        this.cmcsmMap.put("tables", getValue("application.cmcsm.tables"));
        this.cmcsmMap.put("offsetTable", getValue("application.cmcsm.offsetTable"));

        this.cmcsmMap.put("duration", getValue("application.cmcsm.spark.duration"));
        this.cmcsmMap.put("stopGrace", getValue("application.cmcsm.spark.streaming.stopGracefullyOnShutdown"));
        this.cmcsmMap.put("networkTimeout", getValue("application.cmcsm.spark.network.timeout"));
        this.cmcsmMap.put("rpcAskTimeout", getValue("application.cmcsm.spark.rpc.askTimeout"));
        this.cmcsmMap.put("rpcNumRetries", getValue("application.cmcsm.spark.rpc.numRetries"));
        this.cmcsmMap.put("serializer", getValue("application.cmcsm.spark.serializer"));
        this.cmcsmMap.put("kryoRegistrator", getValue("application.cmcsm.spark.kryo.registrator"));

        this.cmcsmMap.put("topic", getValue("application.cmcsm.kafka.topic"));
        this.cmcsmMap.put("partitionSize", getValue("application.cmcsm.kafka.partition.size"));
        this.cmcsmMap.put("bootstrapServers", getValue("application.cmcsm.kafka.bootstrap.servers"));
        this.cmcsmMap.put("keyDeserializer", getValue("application.cmcsm.kafka.key.deserializer"));
        this.cmcsmMap.put("valueDeserializer", getValue("application.cmcsm.kafka.value.deserializer"));
        this.cmcsmMap.put("groupId", getValue("application.cmcsm.kafka.group.id"));
        this.cmcsmMap.put("autoOffsetReset", getValue("application.cmcsm.kafka.auto.offset.reset"));
        this.cmcsmMap.put("enableAutoCommit", getValue("application.cmcsm.kafka.enable.auto.commit"));
        this.cmcsmMap.put("maxPartitionFetchBytes", getValue("application.cmcsm.kafka.max.partition.fetch.bytes"));

        return cmcsmMap;
    }

    public void setCmcsmMap(Map<String, String> cmcsmMap) {
        this.cmcsmMap = cmcsmMap;
    }
}
