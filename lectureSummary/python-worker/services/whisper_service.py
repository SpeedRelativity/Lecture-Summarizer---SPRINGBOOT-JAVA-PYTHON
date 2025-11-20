import whisper
import sys
import os

print("Loading Whisper model...")
whisper_model = whisper.load_model("tiny")
print("Whisper model loaded.")

def transcribe_audio(file_path):
    try:
        if not os.path.isfile(file_path):
            raise FileNotFoundError(f"Audio file not found: {file_path}")
        
        print(f"Transcribing audio file: {file_path}")
        result = whisper_model.transcribe(file_path, fp16=False, verbose=False)
        transcription = result["text"]
        language = result["language"]
        print(f"Transcription completed for file: {file_path}")

        print(f"Detected language: {language}")
        print(f"Transcription: {transcription}")
        return transcription
    except Exception as e:
        print(f"Error transcribing {file_path}: {str(e)}")
        return None
    
    
    