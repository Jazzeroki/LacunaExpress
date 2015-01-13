package com.JazzDevStudio.LacunaExpress.Database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Reference for Inserting arrays: http://stackoverflow.com/questions/10684881/insert-array-in-sqlite-database-in-android
 */
public class DatabaseContract {

	// The "Content authority" is a name for the entire content provider, similar to the
	// relationship between a domain name and its website.  A convenient string to use for the
	// content authority is the package name for the app, which is guaranteed to be unique on the
	// device.
	public static final String CONTENT_AUTHORITY = "com.JazzDevTools.LacunaExpress";
	// Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
	// the content provider.
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	//Strings for the PATH_ variables
	public static final String PATH_MESSAGES = "messages";
	public static final String PATH_EMPIRE = "empire";
	public static final String PATH_WIDGET = "widget";

	// Inner class that defines the table contents of the Messages table
	public static final class MessagesEntry implements BaseColumns {

		public static final Uri CONTENT_URI =
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_MESSAGES).build();

		public static final String CONTENT_TYPE =
				"vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_MESSAGES;
		public static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_MESSAGES;

		// Table name
		public static final String TABLE_NAME = "messages";

		// Stored as a string, message ID. Foreign key
		public static final String COLUMN_MESSAGES_ID = "messages_id";
		// Stored as a string, name of person or people who sent mail (IE Silmarilos)
		public static final String COLUMN_FROM = "from";
		// Stored as a string, name of person or people are having mail sent to them (IE Silmarilos)
		public static final String COLUMN_TO = "to";
		// Stored as a string, name of the subject (IE Fissure Causes Destruction)
		public static final String COLUMN_SUBJECT = "subject";
		// Stored as a string, date of the mail (IE 2015-01-01 +0000. Last five will be cut off when parsing on purpose)
		public static final String COLUMN_DATE = "date";
		// Stored as a string, person or people who are in reply to (IE )
		public static final String COLUMN_IN_REPLY_TO = "in_reply_to";
		// Stored as a string, Body Preview (IE A short snippet: "parliament pass votes 30 for, 0 against...")
		public static final String COLUMN_BODY_PREVIEW = "body_preview";
		// Stored as a string, body of the actual message (IE The actual message)
		public static final String COLUMN_BODY = "body";
		// Stored as sets of strings (Array type), Mail Tags (IE Correspondence, Attack, Spies)
		public static final String COLUMN_TAGS = "tags";
		// Stored as sets of strings (Array type), recipients (IE Silmarilos, TMT, Jazz)
		public static final String COLUMN_RECIPIENTS = "recipients";
		// Stored as an int, Whether or not they have read the mail (IE )
		public static final String COLUMN_HAS_READ = "has_read";
		// Stored as an int, Whether or not they have replied to the mail (IE )
		public static final String COLUMN_HAS_REPLIED = "has_replied";
		// Stored as an int, Whether or not they archived the mail (IE )
		public static final String COLUMN_HAS_ARCHIVED = "has_archived";
		// Stored as an int, Whether or not they have trashed the mail (IE )
		public static final String COLUMN_HAS_TRASHED = "has_trashed";
		// Stored as an int, From ID (IE )
		public static final String COLUMN_FROM_ID = "from_id";
		// Stored as an int, To ID (IE )
		public static final String COLUMN_TO_ID = "to_id";
		// Stored as an int, the mail ID # (IE 231402)
		public static final String COLUMN_MAIL_ID = "id";

		public static Uri buildLocationUri(long id) {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}
	}

	// Inner class that defines the table contents of the Empire table
	public static final class EmpireEntry implements BaseColumns {

		public static final Uri CONTENT_URI =
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_EMPIRE).build();

		public static final String CONTENT_TYPE =
				"vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_EMPIRE;
		public static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_EMPIRE;

		//Table Name: empire (IE Silmarilos' Empire)
		public static final String TABLE_NAME = "empire";

		// Stored as an int, RPC count (IE 2492)
		public static final String COLUMN_RPC_COUNT = "rpc_count";
		// Stored as an int, How many new messages there are (IE 250)
		public static final String COLUMN_HAS_NEW_MESSAGES = "has_new_messages";
		// Stored as a set of Strings (Hashmap output), Planets
		public static final String COLUMN_PLANETS = "planets";
		// Stored as a set of Strings (Hashmap output), Space Stations
		public static final String COLUMN_SPACE_STATIONS = "space_stations";
		// Stored as a set of Strings (Hashmap output), Colonies
		public static final String COLUMN_COLONIES = "colonies";
		// Stored as a set of Strings (Hashmap output), Stations
		public static final String COLUMN_STATIONS = "stations";
		// Stored as a string, Whether or not the colony is set to self-destruct
		public static final String COLUMN_SELF_DESTRUCT_ACTIVE = "self_destruct_active";
		// Stored as a string, When the colony is set to self-destruct
		public static final String COLUMN_SELF_DESTRUCT_DATE = "self_destruct_date";
		// Stored as a string, name of empire (IE Silmarilos)
		public static final String COLUMN_NAME = "name";
		// Stored as a string, status_message
		public static final String COLUMN_STATUS_MESSAGE = "status_message";
		// Stored as a string, Whether or not they are isolationist (IE Yes, NO)
		public static final String COLUMN_IS_ISOLATIONIST = "is_isolationist";
		// Stored as a string, Latest message id.
		public static final String COLUMN_LATEST_MESSAGE_ID = "latest_message_id";
		// Stored as a string, ID of their home planet (IE 491837)
		public static final String COLUMN_HOME_PLANET_ID = "home_planet_id";
		// Stored as a string, name of empire
		public static final String COLUMN_TECH_LEVEL = "tech_level";
		// Stored as a string, essentia amount the player has
		public static final String COLUMN_ESSENTIA = "essentia";
		// Stored as a string, name of empire
		public static final String COLUMN_SERVER = "server";
		// Stored as a string, Alignment
		public static final String COLUMN_Alignment = "alignment";
		// Stored as a string, empire ID. Foreign key
		public static final String COLUMN_EMPIRE_ID = "empire_id";

		//
		public static Uri buildWeatherUri(long id) {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}

		public static Uri buildWeatherLocation(String locationSetting) {
			return CONTENT_URI.buildUpon().appendPath(locationSetting).build();
		}

		public static Uri buildWeatherLocationWithStartDate(
				String locationSetting, String startDate) {
			return CONTENT_URI.buildUpon().appendPath(locationSetting)
					.appendQueryParameter(COLUMN_NAME, startDate).build();
		}

		public static Uri buildWeatherLocationWithDate(String locationSetting, String date) {
			return CONTENT_URI.buildUpon().appendPath(locationSetting).appendPath(date).build();
		}

		public static String getLocationSettingFromUri(Uri uri) {
			return uri.getPathSegments().get(1);
		}

		public static String getDateFromUri(Uri uri) {
			return uri.getPathSegments().get(2);
		}

		public static String getStartDateFromUri(Uri uri) {
			return uri.getQueryParameter(COLUMN_NAME);
		}
	}

	/* Inner class that defines the table contents of the Widget table */
	public static final class WidgetEntry implements BaseColumns {

		public static final Uri CONTENT_URI =
				BASE_CONTENT_URI.buildUpon().appendPath(PATH_WIDGET).build();

		public static final String CONTENT_TYPE =
				"vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_WIDGET;
		public static final String CONTENT_ITEM_TYPE =
				"vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_WIDGET;

		// Table name
		public static final String TABLE_NAME = "widget";

		// Column with the foreign key into the location table.
		public static final String COLUMN_WIDGET_ID = "widget_id";
		// Stored as an int, frequency in minutes that the widget updates (IE 15)
		public static final String COLUMN_SYNC_FREQUENCY = "sync_frequency";
		// Stored as a string, username (IE Silmailos (US1))
		public static final String COLUMN_USERNAME = "username";
		// Stored as a string, total number of messages with no specified tag (IE 1210)
		public static final String COLUMN_MESSAGE_COUNT_ALL = "message_count_all";
		// Stored as an int, total number of messages with received tag correspondence. (IE 15)
		public static final String COLUMN_MESSAGE_COUNT_CORRESPONDENCE = "message_count_correspondence";
		// Stored as an int, total number of messages with received tag tutorial
		public static final String COLUMN_MESSAGE_COUNT_TUTORIAL = "message_count_tutorial";
		// Stored as an int, total number of messages with received tag medal
		public static final String COLUMN_MESSAGE_COUNT_MEDAL = "message_count_medal";
		// Stored as an int, total number of messages with received tag intelligence
		public static final String COLUMN_MESSAGE_COUNT_INTELLIGENCE = "message_count_intelligence";
		// Stored as an int, total number of messages with received tag alert
		public static final String COLUMN_MESSAGE_COUNT_ALERT = "message_count_alert";
		// Stored as an int, total number of messages with received tag attack
		public static final String COLUMN_MESSAGE_COUNT_ATTACK = "message_count_attack";
		// Stored as an int, total number of messages with received tag colonization
		public static final String COLUMN_MESSAGE_COUNT_COLONIZATION = "message_count_colonization";
		// Stored as an int, total number of messages with received tag complaint
		public static final String COLUMN_MESSAGE_COUNT_COMPLAINT = "message_count_complaint";
		// Stored as an int, total number of messages with received tag excavator
		public static final String COLUMN_MESSAGE_COUNT_EXCAVATOR = "message_count_excavator";
		// Stored as an int, total number of messages with received tag mission
		public static final String COLUMN_MESSAGE_COUNT_MISSION = "message_count_mission";
		// Stored as an int, total number of messages with received tag parliament
		public static final String COLUMN_MESSAGE_COUNT_PARLIAMENT = "message_count_parliament";
		// Stored as an int, total number of messages with received tag probe
		public static final String COLUMN_MESSAGE_COUNT_PROBE = "message_count_probe";
		// Stored as an int, total number of messages with received tag spies
		public static final String COLUMN_MESSAGE_COUNT_SPIES = "message_count_spies";
		// Stored as an int, total number of messages with received tag fissure
		public static final String COLUMN_MESSAGE_COUNT_FISSURE = "message_count_fissure";
		// Stored as a string, tag chosen (IE Correspondence)
		public static final String COLUMN_TAG_CHOSEN = "tag_chosen";
		// Stored as a string, choice of color background (IE blue)
		public static final String COLUMN_COLOR_BACKGROUND_CHOICE = "color_background_choice";
		// Stored as a string, choice of color font (IE blue)
		public static final String COLUMN_COLOR_FONT_CHOICE = "color_font_choice";
		// Stored as a String, the Session ID (IE 14th3-jk123k-12345k)
		public static final String COLUMN_SESSION_ID = "session_id";


		public static Uri buildLocationUri(long id) {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}
	}
}
