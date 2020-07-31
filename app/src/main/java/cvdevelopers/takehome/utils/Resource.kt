package cvdevelopers.takehome.utils

data class Resource<out T>(val status: Status, val data: T?, val message: String?, val errorType: Int = 0) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> update(data: T?): Resource<T> {
            return Resource(
                Status.UPDATED,
                data,
                null
            )
        }

        fun <T> error(msg: String, data: T): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> error(msg: String, errorType: Int): Resource<T> {
            return Resource(
                Status.ERROR,
                null,
                msg,
                errorType
            )
        }

        fun <T> error(msg: String): Resource<T> {
            return Resource(Status.ERROR, null, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }
}