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
        transcription = result['text']
        print(f"Transcription completed for file: {file_path}")

        return transcription
    except Exception as e:
        print(f"Error transcribing {file_path}: {str(e)}")
        return None
    

# Windows accepts forward slashes too!
result = transcribe_audio("C:/Users/necha/Desktop/lectureSummary/lectureSummary/audios/test.mp3")

if result:
    print("\n" + "="*60)
    print("TRANSCRIPTION RESULT:")
    print("="*60)
    print(result)
    print("="*60)
else:
    print("❌ Transcription failed!")