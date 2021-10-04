import 'package:flutter/material.dart';

import 'share_service.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Intent test',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({Key? key, required this.title}) : super(key: key);

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _sharedText = 'Empty data';

  @override
  void initState() {
    super.initState();
    // Create the share service
    ShareService()
    // Register a callback so that we handle shared data if it arrives while the
    // app is running
      ..onDataReceived = _handleSharedData


      ..methodChannelName = 'se.matc.dataChannel'

    // Check to see if there is any shared data already, meaning that the app
    // was launched via sharing.
      ..getSharedData().then(_handleSharedData);
  }


  /// Handles any shared data we may receive.
  void _handleSharedData(String sharedData) {

    setState(() {
      _sharedText = sharedData;
      print('_handleSharedData called');
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Text(_sharedText),
      ),
    );
  }

}
