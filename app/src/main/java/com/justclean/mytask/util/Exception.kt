package com.justclean.mytask.util

import java.io.IOException

class APIExceptions(message:String): IOException(message)
class NoInternetException(message: String): IOException(message)