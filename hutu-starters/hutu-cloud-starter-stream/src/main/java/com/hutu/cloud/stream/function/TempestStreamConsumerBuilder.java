package com.hutu.cloud.stream.function;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class TempestStreamConsumerBuilder<T> {

	private final Consumer<T> consumer;

	private Consumer<T> before;

	private Consumer<T> after;

	private TempestStreamConsumerBuilder(Consumer<T> consumer) {
		this(consumer, null, null);
	}

	private TempestStreamConsumerBuilder(Consumer<T> consumer, Consumer<T> before, Consumer<T> after) {
		this.consumer = consumer;
		this.before = before;
		this.after = after;
	}

	public static <T> TempestStreamConsumerBuilder<T> withConsumer(Consumer<T> consumer) {
		return new TempestStreamConsumerBuilder<>(consumer);
	}

	public TempestStreamConsumerBuilder<T> setBefore(Consumer<T> before) {
		this.before = before;
		return this;
	}

	public TempestStreamConsumerBuilder<T> setAfter(Consumer<T> after) {
		this.after = after;
		return this;
	}

	private void hutu(T t) {
		log.info("hutu processing t=[{}]", t.toString());
	}

	public Consumer<T> build() {
		Consumer<T> result = (t) -> {
			hutu(t);
		};
		if (this.before != null) {
			result = result.andThen(this.before);
		}
		result = result.andThen(this.consumer);
		if (this.after != null) {
			result = result.andThen(this.after);
		}
		return result;
	}

}
