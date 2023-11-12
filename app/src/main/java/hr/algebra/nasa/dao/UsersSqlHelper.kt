package hr.algebra.nasa.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hr.algebra.nasa.model.User


private const val DB_NAME = "users.db"
private const val DB_VERSION = 1
private const val TABLE_NAME = "users"
private val CREATE_TABLE_USERS = "create table users( " +
        "${User::id.name} integer primary key, " +
        "${User::uid.name} text not null, " +
        "${User::password.name} text not null, " +
        "${User::firstName.name} text not null, " +
        "${User::lastName.name} text not null, " +
        "${User::username.name} text not null, " +
        "${User::email.name} text not null, " +
        "${User::avatar.name} text not null, " +
        "${User::gender.name} text not null, " +
        "${User::phoneNumber.name} text not null, " +
        "${User::socialInsuranceNumber.name} text not null, " +
        "${User::dateOfBirth.name} text not null " +
        ")"
private const val DROP_TABLE = "drop table $TABLE_NAME"

class UsersSqlHelper(context: Context?)
    : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION),
    UsersRepository {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_USERS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    override fun delete(selection: String?, selectionArgs: Array<String>?) =
        writableDatabase.delete(TABLE_NAME, selection, selectionArgs)

    override fun insert(values: ContentValues?) =
        writableDatabase.insert(TABLE_NAME, null, values)

    override fun query(projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor = readableDatabase.query(
        TABLE_NAME,
        projection,
        selection,
        selectionArgs,
        null,
        null,
        sortOrder
    )

    override fun update(values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?
    ) = writableDatabase.update(TABLE_NAME, values, selection, selectionArgs)


}