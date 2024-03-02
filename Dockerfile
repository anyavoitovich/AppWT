FROM openjdk:11-jdk-slim

ENV ANDROID_SDK_VERSION=7583922

RUN apk add --no-cache unzip wget zip && rm -rf /var/lib/apk/cache

# Скачивание и установка SDK
RUN wget -q https://dl.google.com/android/repository/commandlinetools-linux-${ANDROID_SDK_VERSION}_latest.zip -O /tmp/tools.zip && \
  unzip -q /tmp/tools.zip -d /opt/android-sdk && \
  rm -v /tmp/tools.zip

# Установка пакетов SDK
RUN ${ANDROID_HOME}/tools/bin/sdkmanager --update && \
  ${ANDROID_HOME}/tools/bin/sdkmanager --install "platform-tools" "build-tools;33.0.0" "platforms;android-33"

ENV PATH="${PATH}:${ANDROID_HOME}/tools:${ANDROID_HOME}/tools/bin:${ANDROID_HOME}/platform-tools"

# Копирование исходного кода (измените на ваш проект)
COPY . /app

WORKDIR /app

# Запуск команды сборки (измените на вашу команду)
CMD ["./gradlew", "assembleDebug"]

