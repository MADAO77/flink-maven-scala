package com.opensourceteams.module.bigdata.flink.example.tableapi.source.csv

import org.apache.flink.api.scala.{ExecutionEnvironment, _}
import org.apache.flink.table.api.{TableEnvironment, Types}
import org.apache.flink.table.sources.CsvTableSource

object Run {



  def main(args: Array[String]): Unit = {


    //得到批环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    //创建CsvTableSource
    val csvTableSource = CsvTableSource.builder()
      .path("src/main/resources/data/csv/user.csv")
      .ignoreFirstLine()
      .fieldDelimiter(",")
      .field("name", Types.STRING)
      .field("age", Types.INT)
      .build()


    //得到Table环境
    val tableEnv = TableEnvironment.getTableEnvironment(env)
    //注册table
    tableEnv.registerTableSource("user",csvTableSource)




    //查询表数据
    val table = tableEnv.scan("user")

    //把table数据转成 批数据
    val dataSet = tableEnv.toDataSet[(String, Integer)](table)

    //打钱输出
    dataSet.print()
  }

}
