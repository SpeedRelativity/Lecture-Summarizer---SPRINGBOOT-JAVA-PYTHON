import yt_dlp as ytdlp
import sys
from pathlib import Path
import os

def download(job,url):

    try:

        base_path = os.path.dirname(os.path.abspath(__file__))
        output_path = os.path.join(base_path, 'audios')

        if not os.path.exists(output_path):
            os.makedirs(output_path)

        ydl_opts = {
            'format': 'best',
            'outtmpl': f'{output_path}/%(id)s.%(ext)s',
            'postprocessors': [{
                'key': 'FFmpegExtractAudio',
                'preferredcodec': 'mp3',
                'preferredquality': '192',
            }],
            
            "overwrite": True,
            "nopostoverwrites": False,
            }

        with ytdlp.YoutubeDL(ydl_opts) as ydl:
            ydl.download([url])
            info = ydl.extract_info(url, download=False)
            video_id = info.get("id")

        return f"{output_path}/{video_id}.mp3"
    except Exception as e:
        print(f"Error downloading {url}: {str(e)}")
        return False
