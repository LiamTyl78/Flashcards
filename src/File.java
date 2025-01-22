public class File {
    String name, path;

    public File(String name, String path){
        this.name = name;
        this.path = path;
    }

    public String getPath(){
        return this.path;
    }

    @Override
    public String toString(){
        return this.name;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj instanceof File) {
            File otherFile = (File) obj;
            return otherFile.getPath().equals(this.getPath());
        }
        return false;
    }
}
