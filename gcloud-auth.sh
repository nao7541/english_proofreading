#!/bin/bash

# gcloud認証
gcloud config set project $GOOGLE_CLOUD_PROJECT
gcloud auth apprication-default login $GMAIL_ADDRESS

# アプリケーション起動
exec java -jar app.jar