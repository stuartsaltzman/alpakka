/*
 * Copyright (C) 2016-2018 Lightbend Inc. <http://www.lightbend.com>
 */

package akka.stream.alpakka.sqs.javadsl

import java.util.concurrent.CompletionStage

import akka.Done
import akka.stream.alpakka.sqs._
import akka.stream.javadsl.Sink
import com.amazonaws.services.sqs.AmazonSQSAsync
import akka.stream.scaladsl.{Flow, Keep}

import com.amazonaws.services.sqs.model.SendMessageRequest

import scala.collection.JavaConverters._
import scala.compat.java8.FutureConverters.FutureOps

/**
 * Java API to create SQS Sinks.
 */
object SqsPublishSink {

  /**
   * creates a [[akka.stream.javadsl.Sink Sink]] that accepts strings and publishes them as messages to a SQS queue using an [[com.amazonaws.services.sqs.AmazonSQSAsync AmazonSQSAsync]]
   */
  def create(queueUrl: String,
             settings: SqsPublishSettings,
             sqsClient: AmazonSQSAsync): Sink[String, CompletionStage[Done]] =
    scaladsl.SqsPublishSink.apply(queueUrl, settings)(sqsClient).mapMaterializedValue(_.toJava).asJava

  /**
   * creates a [[akka.stream.javadsl.Sink Sink]] to publish messages to a SQS queue using an [[com.amazonaws.services.sqs.AmazonSQSAsync AmazonSQSAsync]]
   */
  def messageSink(queueUrl: String,
                  settings: SqsPublishSettings,
                  sqsClient: AmazonSQSAsync): Sink[SendMessageRequest, CompletionStage[Done]] =
    scaladsl.SqsPublishSink
      .messageSink(queueUrl, settings)(sqsClient)
      .mapMaterializedValue(_.toJava)
      .asJava

  /**
   * creates a [[akka.stream.javadsl.Sink Sink]] to publish messages to SQS queues based on the message queue url using an [[com.amazonaws.services.sqs.AmazonSQSAsync AmazonSQSAsync]]
   */
  def messageSink(settings: SqsPublishSettings,
                  sqsClient: AmazonSQSAsync): Sink[SendMessageRequest, CompletionStage[Done]] =
    scaladsl.SqsPublishSink
      .messageSink(settings)(sqsClient)
      .mapMaterializedValue(_.toJava)
      .asJava

  /**
   * creates a [[akka.stream.javadsl.Sink Sink]] that groups strings and publishes them as messages in batches to a SQS queue using an [[com.amazonaws.services.sqs.AmazonSQSAsync AmazonSQSAsync]]
   * @see https://doc.akka.io/docs/akka/current/stream/operators/Source-or-Flow/groupedWithin.html#groupedwithin
   */
  def grouped(queueUrl: String,
              settings: SqsPublishGroupedSettings,
              sqsClient: AmazonSQSAsync): Sink[String, CompletionStage[Done]] =
    scaladsl.SqsPublishSink.grouped(queueUrl, settings)(sqsClient).mapMaterializedValue(_.toJava).asJava

  /**
   * creates a [[akka.stream.javadsl.Sink Sink]] that groups messages and publishes them in batches to a SQS queue using an [[com.amazonaws.services.sqs.AmazonSQSAsync AmazonSQSAsync]]
   * @see https://doc.akka.io/docs/akka/current/stream/operators/Source-or-Flow/groupedWithin.html#groupedwithin
   */
  def groupedMessageSink(queueUrl: String,
                         settings: SqsPublishGroupedSettings,
                         sqsClient: AmazonSQSAsync): Sink[SendMessageRequest, CompletionStage[Done]] =
    scaladsl.SqsPublishSink
      .groupedMessageSink(queueUrl, settings)(sqsClient)
      .mapMaterializedValue(_.toJava)
      .asJava

  /**
   * creates a [[akka.stream.javadsl.Sink Sink]] that accepts an iterable of strings and publish them as messages in batches to a SQS queue using an [[com.amazonaws.services.sqs.AmazonSQSAsync AmazonSQSAsync]]
   * @see https://doc.akka.io/docs/akka/current/stream/operators/Source-or-Flow/groupedWithin.html#groupedwithin
   */
  def batch(queueUrl: String,
            settings: SqsPublishBatchSettings,
            sqsClient: AmazonSQSAsync): Sink[java.lang.Iterable[String], CompletionStage[Done]] =
    Flow[java.lang.Iterable[String]]
      .map(_.asScala)
      .toMat(scaladsl.SqsPublishSink.batch(queueUrl, settings)(sqsClient))(Keep.right)
      .mapMaterializedValue(_.toJava)
      .asJava

  /**
   * creates a [[akka.stream.javadsl.Sink Sink]] to publish messages in batches to a SQS queue using an [[com.amazonaws.services.sqs.AmazonSQSAsync AmazonSQSAsync]]
   */
  def batchedMessageSink(
      queueUrl: String,
      settings: SqsPublishBatchSettings,
      sqsClient: AmazonSQSAsync
  ): Sink[java.lang.Iterable[SendMessageRequest], CompletionStage[Done]] =
    Flow[java.lang.Iterable[SendMessageRequest]]
      .map(_.asScala)
      .toMat(scaladsl.SqsPublishSink.batchedMessageSink(queueUrl, settings)(sqsClient))(Keep.right)
      .mapMaterializedValue(_.toJava)
      .asJava
}
