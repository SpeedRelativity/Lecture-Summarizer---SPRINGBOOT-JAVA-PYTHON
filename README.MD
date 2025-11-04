# Currently working on:

- UI stuff -- need to create the final display screen.
- PYTHON WORKER IS FINISHED. TRANSCRIPTION AND SUMMARIZATION WORKS.
- NEED TO CONNECT FRONTEND TO BACKEND, WORK ON DATABASE NEXT.
- Showing a list of queued jobs in processing.tsx -- works
- UI for submission page could be better.
- Build out transcription page -- not started

# Lecture Summarizer

Example:

PS C:\Users\necha\Desktop\lectureSummarize\lecturesummary> python worker.py 1000 https://www.youtube.com/watch?v=Nne_Vwmfew8
Loading Whisper model...
Whisper model loaded.
[youtube] Extracting URL: https://www.youtube.com/watch?v=Nne_Vwmfew8
[youtube] Nne_Vwmfew8: Downloading webpage
[youtube] Nne_Vwmfew8: Downloading tv client config
[youtube] Nne_Vwmfew8: Downloading tv player API JSON
[youtube] Nne_Vwmfew8: Downloading web safari player API JSON
[youtube] Nne_Vwmfew8: Downloading m3u8 information
[info] Nne_Vwmfew8: Downloading 1 format(s): 96
[download] Sleeping 4.00 seconds as required by the site...
[hlsnative] Downloading m3u8 manifest
[hlsnative] Total fragments: 7
[download] Destination: C:\Users\necha\Desktop\lectureSummarize\lecturesummary\audios\Nne_Vwmfew8.mp4
[download] 100% of 9.06MiB in 00:00:00 at 9.22MiB/s
[FixupM3u8] Fixing MPEG-TS in MP4 container of "C:\Users\necha\Desktop\lectureSummarize\lecturesummary\audios\Nne_Vwmfew8.mp4"
[ExtractAudio] Destination: C:\Users\necha\Desktop\lectureSummarize\lecturesummary\audios\Nne_Vwmfew8.mp3
Deleting original file C:\Users\necha\Desktop\lectureSummarize\lecturesummary\audios\Nne_Vwmfew8.mp4 (pass -k to keep)
[youtube] Extracting URL: https://www.youtube.com/watch?v=Nne_Vwmfew8
[youtube] Nne_Vwmfew8: Downloading webpage
[youtube] Nne_Vwmfew8: Downloading tv client config
[youtube] Nne_Vwmfew8: Downloading tv player API JSON
[youtube] Nne_Vwmfew8: Downloading web safari player API JSON
[youtube] Nne_Vwmfew8: Downloading m3u8 information
Transcribing audio file: C:\Users\necha\Desktop\lectureSummarize\lecturesummary\audios/Nne_Vwmfew8.mp3
Detected language: English
100%|█████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████████| 3009/3009 [00:01<00:00, 2562.67frames/s]
Transcription completed for file: C:\Users\necha\Desktop\lectureSummarize\lecturesummary\audios/Nne_Vwmfew8.mp3
Detected language: en
Transcription: You sell it, we ship it. Put the strength of Amazon's technology and transportation network behind your business. When you ship with us, your brand names on the box. Get seamless pickups, transparent rates and delivery, and two to five days. With daily drops and proof on arrival, your packages come with a smile. Don't just send a package, send a message, ground shipping, built for what's next.
You sell it, we ship it. Put the strength of Amazon's technology and transportation network behind your business. When you ship with us, your brand names on the box. Get seamless pickups, transparent rates and delivery, and two to five days. With daily drops and proof on arrival, your packages come with a smile. Don't just send a package, send a message, ground shipping, built for what's next.
Generating summary...
Summary generated.
**Summary:**
The lecture introduces Amazon's shipping service, highlighting its benefits for businesses. Key features include:

- Use of Amazon's technology and transportation network
- Branded packaging with the business's name on the box
- Seamless pickups and transparent rates
- Fast delivery (2-5 days)
- Daily drops and proof of arrival
- Reliable and trackable shipping

**Study Notes:**

- Amazon's shipping service is designed to support businesses with their logistics needs
- The service offers a range of benefits, including branded packaging and fast delivery
- Businesses can rely on Amazon's technology and transportation network to ensure seamless and transparent shipping.
  Download completed for job 1000: **Summary:**
  The lecture introduces Amazon's shipping service, highlighting its benefits for businesses. Key features include:

- Use of Amazon's technology and transportation network
- Branded packaging with the business's name on the box
- Seamless pickups and transparent rates
- Fast delivery (2-5 days)
- Daily drops and proof of arrival
- Reliable and trackable shipping

**Study Notes:**

- Amazon's shipping service is designed to support businesses with their logistics needs
- The service offers a range of benefits, including branded packaging and fast delivery
- Businesses can rely on Amazon's technology and transportation network to ensure seamless and transparent shipping.
