import { HttpEvent, HttpEventType } from '@angular/common/http';
import { Component } from '@angular/core';
import { UploadService } from 'src/app/service/upload.service';

@Component({
  selector: 'app-upload-files',
  templateUrl: './upload-files.component.html',
  styleUrls: ['./upload-files.component.sass']
})
export class UploadFilesComponent {

  fileNames: String[] = [];
  filesToUpload: File[] = [];
  message: string = "";
  progress: { percentage: number; fileName: string } = {
    percentage: 0,
    fileName: ''
  };

  constructor(private uploadService: UploadService) {}

  async getFiles(event: any) {
    const files = event.target.files;
    const filesQtt = files.length;

    for (let i = 0; i < filesQtt; i++) {
      const file = files.item(i);
      const fileContent = await this.readFileContent(file);
      const content = this.removeAvgPrice(fileContent);
      const newFile = new File([content], file.name, { type: "text/xml" });

      this.fileNames.push(file.name);
      this.filesToUpload.push(newFile);
    }
  }

  removeAvgPrice(content: string): string {
    let init = content.indexOf("<precoMedio>");

    while (init != -1) {
      let end = content.indexOf("</precoMedio>");
      let fragment = content.substring(init, end + 13);
      content = content.replace(fragment, "");
      init = content.indexOf("<precoMedio>");
    }

    return content;
  }

  readFileContent(file: File): Promise<string> {
    return new Promise<string>((resolve, reject) => {
      if (!file) {
        resolve('');
      }

      const reader = new FileReader();

      reader.onload = (e) => {
        const text = reader.result!.toString();
        resolve(text);
      };

      reader.readAsText(file);
    });
  }

  uploadFiles(recursiveCall = false) {
    const filesQtt = this.filesToUpload.length;

    if (filesQtt <= 0 && !recursiveCall) {
      this.message = "Não existem arquivos para serem importados. Por favor, recarregue a página e tente novamente."
      return;
    }

    if (filesQtt <= 0 && recursiveCall) {
      this.message = "Todos os arquivos foram importados com sucesso."
      return;
    }

    const file = this.filesToUpload[0];

    const subscription = this.uploadService.upload(file)
      .subscribe({
        next: (event: HttpEvent<Object>) => {
          if(event.type === HttpEventType.Response) {
            // upload concluído
            const fileIndex = this.filesToUpload.indexOf(file);
            this.filesToUpload.splice(fileIndex, 1);
            this.progress.fileName = "";

            this.uploadFiles(true);
            subscription.unsubscribe();
          } else if (event.type === HttpEventType.UploadProgress) {
            // upload em progresso
            this.progress.fileName = file.name;
          }
        },
        error: e => {
          this.message = `Ocorreu um erro durante o upload do arquivo: ${file.name}. Por favor, recarregue a página e tente novamente.`
        }
      });

      return false;
  }

}
