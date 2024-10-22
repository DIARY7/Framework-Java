## Sprint 12
Afaka mandefa fichier ,
Ao anaty Controleur developpeur :

@POST
    @GetMapping("/traitement_input_file")
    public ModelView traitement_input_file( @Param(name="fichier") Part fichier,Part fichier2){
        ModelView mv = new ModelView();
        /* Uploadena ilay fichier */
        String pathUpload = "F:\\S4\\WEB-DYNAMIQUE\\Framework\\test-framework\\";
        try {
            fichier.write(pathUpload+fichier.getSubmittedFileName());
            fichier2.write(pathUpload+fichier2.getSubmittedFileName());
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("L'exception dans controleur 2 traitement_input_file "+ e);
            mv.addObject("destination", pathUpload+fichier.getSubmittedFileName());
        }
        mv.setUrl("page/page12miakatra/input-file.jsp");
        return mv;
    }



