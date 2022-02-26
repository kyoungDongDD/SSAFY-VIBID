<template>
  <quill-editor
    id="editor"
    v-model="content"
    ref="myQuillEditor"
    :options="editorOption"
    @change="onEditorChange($event)"
  />
</template>

<script>
import quillEditor from "../../../node_modules/vue-quill-editor/src/editor.vue";
import "@vueup/vue-quill/dist/vue-quill.snow.css";

export default {
  props: ["quillContent", "checkReadonly", "checkTool"],
  components: {
    quillEditor,
  },
  data: () => ({
    content: [],
    editorOption: {
      //debug: "info",
      placeholder: "Type your post..",
      theme: "snow",
      modules: {
        toolbar: [
          ["bold", "italic", "underline", "strike"],
          ["blockquote", "code-block"],
          [{ header: 1 }, { header: 2 }],
          [{ list: "ordered" }, { list: "bullet" }],
          [{ script: "sub" }, { script: "super" }],
          [{ indent: "-1" }, { indent: "+1" }],
          [{ direction: "rtl" }],
          [{ size: ["small", false, "large", "huge"] }],
          [{ header: [1, 2, 3, 4, 5, 6, false] }],
          [{ color: [] }, { background: [] }],
          [{ font: [] }],
          [{ align: [] }],
          ["clean"],
          ["link", "image", "video"],
        ],
      },
    },
    delta: undefined,
  }),
  methods: {
    getParentContents() {
      this.$refs.myQuillEditor.quill.setContents(this.quillContent);
    },
    onEditorChange({ quill, html, text }) {
      console.log("editor change!", quill, html, text);
      //this.content = html;
      this.content = this.$refs.myQuillEditor.quill.getContents();
      //console.log(this.content);
      //console.log(JSON.stringify(this.content.ops));
      this.$emit("update-content", JSON.stringify(this.content.ops));
    },
    computed: {
      editor() {
        return this.$refs.myQuillEditor.quill;
      },
    },
  },
  // this.delta = [
  //   { insert: "asdasd" },
  //   { insert: "\n" },
  //   { insert: "world", attributes: { bold: true } },
  // ];
  // this.$refs.myQuillEditor.quill.setContents(this.delta);
  // console.log();
  mounted() {
    if (this.checkReadonly == true) {
      console.log(this.$refs.myQuillEditor.quill.getModule("toolbar"));
      console.log(this.checkTool);
      this.$refs.myQuillEditor.quill.enable(false);
    }
    if (this.checkTool == false) {
      document.querySelector(".ql-toolbar").style.display = "none";
      document.querySelector(".ql-container").style.border =
        "1px solid #d1d5db";
    }
    this.getParentContents();
  },
};
</script>
<style>
.ql-editor {
  height: 50vh;
  margin-bottom: 2%;
}
.ql-container {
  height: auto;
}
</style>
