# **SpotiShut!** - Silence Spotify Ads &middot; [![GitHub license](https://img.shields.io/badge/license-GPL--3.0-orange)](https://github.com/Carball0/sshsec/blob/main/LICENSE) 

A simple Java program that silences ads from the Spotify Free version on Windows.

## How it works
The program detects the window title from Spotify, and determines when you pause the playback, when you resume it and when an ad is playing. 

Whenever your program is NOT playing music, the audio will become muted until the playback is resumed.

## Compatibility and dependencies
The following requirements must be met for installing SpotiShut! (Development for Linux based systems is on the way!):
```
- Windows 7 64-bits or higher
- Latest Java JRE
- Spotify app (traditional or Microsoft Store)
- SoundVolumeView from nirsoft stored on Windows\System32\
```
**IMPORTANT:** You must download SoundVolumeView.exe from [here](https://github.com/Carball0/SpotiShut/blob/main/install/SoundVolumeView.exe?raw=true) 
(this repo) or [here](https://www.nirsoft.net/utils/soundvolumeview-x64.zip) (latest on nirsoft webpage, you must unzip it first). 
Move the executable under the System32 folder (usually ````C:\Windows\System32\```` ).

If it is not detected, a warning will be shown indicating a dependency error.


## Installation

### The quick way - Click and run
To run the program make sure you have read the dependencies section, 
[download the jar file](https://github.com/Carball0/SpotiShut/blob/main/SpotiShut.jar?raw=true) and just double click
the executable while having the Spotify app open. 


Now your Spotify ads will be silenced! You can manually close the app or let it be automatically closed when you stop
using Spotify.

### The complete way - Shortcut
A shortcut may be configured to make it easier and faster to start listening to music without ads.

Make sure you have followed all the previous steps (set dependencies and [download the jar](https://github.com/Carball0/SpotiShut/blob/main/SpotiShut.jar?raw=true)), 
and additionally download the [following bat file](https://github.com/Carball0/SpotiShut/blob/main/SpotiShutExec.bat?raw=true). 

To set up the shortcut, modify your already existing desktop shortcut and configure it to execute the bat file,
which will execute both programs at the same time:

- Move the **bat file** and **jar file** to ````%appdata%```` (write ````%appdata%```` on file explorer and it will take you there)
- Right click on **Spotify Shortcut -> Properties -> Target**
- Change the content on "Target" to "````%appdata%/SpotiShutExec.bat````"

Now your Spotify shortcut will open both programs. You may customize the bat file if you choose another location for the executable files.


## Why SpotiShut and not an ad blocker?
Ad blockers usually block known ad domains so the Spotify app cannot retrieve the ads, and this way they won't be
shown. In theory, this is a better way of blocking ads, but comes with some caveats:
- Ad domains change overtime, so ads will eventually be shown again
- Some blocked domains break Spotify's functions. This means that the app may crash or not play some or any song
- Spotify may notice what you're doing and ban your account, as it is against their
 [User Guidelines](https://www.spotify.com/en/legal/user-guidelines/)

Silencing will result in a more reliable and secure way of avoiding ads, if you can tolerate about 20-60 seconds of silence 
between ads.