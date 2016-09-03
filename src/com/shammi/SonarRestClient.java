package com.shammi;

import java.util.List;
import java.util.function.Consumer;

import org.sonar.wsclient.Host;
import org.sonar.wsclient.Sonar;
import org.sonar.wsclient.connectors.HttpClient4Connector;
import org.sonar.wsclient.services.Measure;
import org.sonar.wsclient.services.Metric;
import org.sonar.wsclient.services.MetricQuery;
import org.sonar.wsclient.services.Resource;
import org.sonar.wsclient.services.ResourceQuery;

public class SonarRestClient {

	public static void main(String[] args) {
		Sonar sonar = new Sonar(new HttpClient4Connector(new Host("https://sonarqube.com/")));

		List<Resource> resources = sonar.findAll(getResourceQuery());

		// getMetrics(sonar);

		for (Resource r : resources) {
			System.out.println(r.getLongName());
			System.out.println("--------------------");
			r.getMeasures().stream().forEach(new Consumer<Measure>() {

				@Override
				public void accept(Measure t) {
					System.out.println(t.getMetricKey() + "=" + t.getValue());
				}
			});

			System.out.println("\n");
		}

	}

	public static ResourceQuery getResourceQuery() {
		ResourceQuery rq = new ResourceQuery();
		rq.setMetrics("lines", "violations", "open_issues");
		rq.setLimit(2);
		return rq;
	}

	private static void getMetrics(Sonar sonar) {
		List<Metric> metrics = sonar.findAll(MetricQuery.all());
		metrics.stream().forEach(new Consumer<Metric>() {

			@Override
			public void accept(Metric t) {
				System.out.println("Key:" + t.getKey());

			}
		});
	}
}
