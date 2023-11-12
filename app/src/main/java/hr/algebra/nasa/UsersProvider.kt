package hr.algebra.nasa

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import hr.algebra.nasa.dao.UsersRepository
import hr.algebra.nasa.factory.getUsersRepository
import hr.algebra.nasa.model.User

private const val AUTHORITY = "hr.algebra.nasa.api.provider"
private const val PATH = "users"
val USERS_PROVIDER_CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$PATH")

private const val USERS = 10
private const val USER_ID = 20
private val URI_MATCHER = with(UriMatcher(UriMatcher.NO_MATCH)) {
    addURI(AUTHORITY, PATH, USERS)
    addURI(AUTHORITY, "$PATH/#", USER_ID)
    this
}


class UsersProvider : ContentProvider() {

    private lateinit var usersRepository: UsersRepository

    override fun onCreate(): Boolean {
        usersRepository = getUsersRepository(context)
        return  true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? = usersRepository.query(projection,selection,selectionArgs,sortOrder)

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val id = usersRepository.insert(values)
        return ContentUris.withAppendedId(USERS_PROVIDER_CONTENT_URI, id)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        when(URI_MATCHER.match(uri)) {
            USERS -> return usersRepository.delete(selection, selectionArgs)
            USER_ID ->
                uri.lastPathSegment?.let {
                    return usersRepository.delete("${User::id.name}=?", arrayOf(it))
                }
        }
        throw java.lang.IllegalArgumentException("No such uri")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        when(URI_MATCHER.match(uri)) {
            USERS -> return usersRepository.update(values, selection, selectionArgs)
            USER_ID ->
                uri.lastPathSegment?.let {
                    return usersRepository.update(values, "${User::id.name}=?", arrayOf(it))
                }
        }
        throw java.lang.IllegalArgumentException("No such uri")
    }
}