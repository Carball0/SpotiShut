# **SpotiShut!** - Silence Spotify Ads &middot; [![GitHub license](https://img.shields.io/badge/license-GPL--3.0-orange)](https://github.com/Carball0/sshsec/blob/main/LICENSE) 

A simple Java program that silences ads from the Spotify Free version on Windows.

## [How it works](#how-it-works)
The program detects the window title from Spotify, and determines wether and ad is playing or your music is playing or not.

Whenever your program is NOT playing music, the audio will become muted until the playback is resumed.

## [Compatibility and dependencies](#compatibility-and-dependencies)
The following requirements must be met for installing SpotiShut! (Development for Linux is on the way!):
```
- Windows 7 64-bits or higher
- Latest Java JRE
- Spotify app (traditional or Microsoft Store)
- SoundVolumeView from nirsoft
```
SoundVolumeView will be automatically installed the first time the app is executed.

## [Installation](#installation)

### The quick way - Click and run
[Download the jar file](https://github.com/Carball0/SpotiShut/blob/main/SpotiShut.jar?raw=true) and just double click
the executable while having the Spotify app open.


Now your Spotify ads will be silenced! You can manually close the app or let it be automatically closed when you stop
using Spotify.

### The complete way - Shortcut
A shortcut may be configured to make it easier and faster to start listening to music without ads.

To set it up, modify your already existing desktop shortcut:

- [Download the jar file](https://github.com/Carball0/SpotiShut/blob/main/SpotiShut.jar?raw=true) and execute it at least one time.
- Right click on **Spotify Shortcut -> Properties -> Target**
- Change the content on "Target" to "````cmd /c %appdata%\SpotiShut\SpotiShutExec.bat````"

## [Components](#components)
The application creates a new folder inside ````%appdata%```` and extracts the content from ````/src/SoundVolumeView.zip````. The contents under ````%appdata%/SpotiShut/```` 
are the following:

```
- **SoundVolumeView.exe**: Software from nirsoft, essential for silencing ads
- **SpotiShutExec.bat**: Bat file for executing Spotify and SpotiShut at the same time, used for the shortcut.
- **SpotiShut.jar**: The executable file is copied to this folder. The original can be deleted.
```

For uninstalling, simply delete the contents in ````%appdata%/SpotiShut/````



## [Why SpotiShut and not an ad blocker?](#why-spotishut-and-not-an-ad-blocker)
Ad blockers usually block known ad domains so the Spotify app cannot retrieve the ads, and this way they won't be
shown. In theory, this is a better way of blocking ads, but comes with some caveats:
- Ad domains change overtime, so ads will eventually be shown again
- Some blocked domains break Spotify's functions. This means that the app may crash or not play some or any song
- Spotify may notice what you're doing and ban your account, as it is against their
 [User Guidelines](https://www.spotify.com/en/legal/user-guidelines/)

Silencing will result in a more reliable and secure way of avoiding ads, if you can tolerate about 20-60 seconds of silence 
between ads.
