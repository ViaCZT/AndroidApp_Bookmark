# Task 2: Functionality

The diagram in the previous task showed URLs appearing in the `ListView`. We'll approach adding this functionality, and several other things, in this task.

**Note:** This task contains several code snippets, but it is not sufficient to simply copy-paste these into your program. You will need to consider what arguments some functions take, and how to structure your program.



## Setting up the `ArrayAdapter`

An array list is a reasonable data structure for a small number of URLs modified on the fly. Create one, and add some URLs to it:

```java
your_array = new ArrayList<>();
your_array.add("https://www.anu.edu.au/");
your_array.add("https://www.google.com/");
```

To link this array to the `ListView`, you need to create an ArrayAdapter: `ArrayAdapter adapter = new ArrayAdapter(...);`.

>>>
### Info
What is an `ArrayAdapater` actually? Let's understand what is going on here. We have an array that is storing objects in memory. We also have an Android Studio `ListView` object. The `ArrayAdapter` acts as the **interface** for the `ListView` object to access our URL array. It's like the 'link' between a `ListView` (and other views which you can experiment with) and an array.
>>>

 The layout resource (which must be passed as a parameter to `ArrayAdapater`'s constructor) is `android.R.layout.simple_list_item_1`. Here, `simple_list_item_1` is a built-in XML layout for presenting a list of text items.

After capturing the `ListView` from `layout` (you'll want to use `findViewById`, and don't forget to cast the output to type `ListView`), you will need to set the adapter used by the `ListView`: `your_ListView.setAdapter(adapater);`



## Adding URLs to the `ListView`

When the user inputs a string into the `EditText` field and clicks the "Add URL!" button, it should appear in the list below. Here is one plausible approach for this functionality:

- Capture your `Button` from `layout`.
- Set an `OnClickListener` to the button. This listener should fetch the content written to the `EditText` view and insert it into the `ListView`.
- For this, capture the `EditText` view, and add its content to an array (as shown in the Android lectures). Then, clear the `EditText`, by settings its content to the empty string `""`.
- Finally, to trigger a visual update in the `ListView`, notify the ArrayAdapter that the array was updated: `your_ArrayAdapter.notifyDataSetChanged();`



## Listening for clicks on the `ListView`

When the user clicks on an item in the ListView, that page should open in a web browser.

Create a listener for the ListView: `your_ListView.setOnItemClickListener(some_listener);` The `setOnItemClickListener` allows you to retrieve the item clicked from the array, whereas a standard click listener would only tell you that somewhere in the array has been clicked.

In the callback, you will want to create an `Intent` to go from the current activity to the activity containing the `WebView`, add some data to the intent to make it available to the next activity, and run the intent.

```java
Intent intent = new Intent(...);
intent.putExtra("URL", your_array.get(i).toString());
startActivity(...)
```  

The OnItemClickListener must receive the position of the clicked item (variable `i`). The variable `URL` will hold this string within the `Intent`.

>>>
### Important
If this is not making sense, **watch the Android Studio lectures**. The code will be explained in much more detail.
>>>



## Loading URLs in the `WebView`

In Android Studio development, whenever we run an intent in one activity that takes us to a different activity, we will normally want code to receive the information sent and do *something* with it.

In the Activity containing the WebView, you must:

Get the intent and the data sent from the main activity. How do you do this? Use the methods `getIntent` and `getStringExtra` to access the string assigned in the previous activity.

You must check if the URL is invalid (that is, it is either `null` or `""`). If so, a toast message must be displayed (`"URL is invalid"`). Otherwise, capture the `WebView` and use the method `loadUrl` to load the passed URL.
  
Some websites may open a new browser. This is totally fine.

>>>
#### Important
**This is highly important**. Ensure that your app has access to the Internet. This is done by placing the following in the manifest file,
`<uses-permission android:name="android.permission.INTERNET" />`.
>>>



## Marking

To recieve marks, you should be able to add items to the `ListView` by typing a URL into the `EditText`, and each clicked item should redirect to a new activity
containing a WebView loading the corresponding URL, displaying a toast if the URL is invalid.

>>>
### Important
Your **must** show your tutor your app to receive a mark. You can also show a drop-in tutor before the submission deadline.
>>>

The lab video shows how your app should act in a demonstration.
