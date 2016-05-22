### Elasticsearch installation

You can use the default config, but I suggest you install `kopf` and `head` plugins. They make cluster management easier.
https://github.com/lmenezes/elasticsearch-kopf
https://mobz.github.io/elasticsearch-head/

Using the `kopf` plugin, install the following index template:
```
{
  "order": 0,
  "template": "logstash_metrics",
  "settings": {},
  "mappings": {
    "my_app_metrics": {
      "dynamic": "false",
      "properties": {
        "status_code": {
          "type": "integer"
        },
        "@timestamp": {
          "type": "date"
        },
        "request_body": {
          "index": "not_analyzed",
          "type": "string"
        },
        "transportation_mode": {
          "index": "not_analyzed",
          "type": "string"
        },
        "client": {
          "index": "not_analyzed",
          "type": "string"
        },
        "request_path": {
          "index": "not_analyzed",
          "type": "string"
        },
        "response_time": {
          "type": "long"
        },
        "request_id": {
          "index": "not_analyzed",
          "type": "string"
        },
        "travel_time": {
          "type": "integer"
        }
      }
    }
  },
  "aliases": {}
}
```

### Logstash installation

Start with the included configuration file:
`bin/logstash -f logstash.conf`

### Filebeat installation

Copy `filebeat.yml` to the extracted location and run filesbeat.

### Kibana installation

Use default configuration

### Example application

Install `typesafe activator` and do `activator run` from inside `application` folder

### Run Request Maker

Install `typesafe activator` and do `activator run` from inside `request-maker` folder

This will start sending many requests to `http://localhost:9000` so you have some data to work with.
