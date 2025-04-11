package com.blummock.core.dispatcher

import kotlinx.coroutines.Dispatchers

class DispatcherProviderImpl  {
  val io by lazy { Dispatchers.IO }
    val main by lazy { Dispatchers.Main }
    val mainImmediate by lazy { Dispatchers.Main.immediate }
   val default by lazy { Dispatchers.Default }
}