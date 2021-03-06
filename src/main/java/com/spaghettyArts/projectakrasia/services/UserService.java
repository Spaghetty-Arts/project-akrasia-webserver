package com.spaghettyArts.projectakrasia.services;

import com.spaghettyArts.projectakrasia.model.HistoryModel;
import com.spaghettyArts.projectakrasia.model.ResetModel;
import com.spaghettyArts.projectakrasia.model.UserModel;
import com.spaghettyArts.projectakrasia.repository.HistoryRepository;
import com.spaghettyArts.projectakrasia.repository.ResetRepository;
import com.spaghettyArts.projectakrasia.repository.UserRepository;
import com.spaghettyArts.projectakrasia.utils.Matches;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;

import static com.spaghettyArts.projectakrasia.utils.DailyReward.getDaily;
import static com.spaghettyArts.projectakrasia.utils.DateValidation.updateLogin;
import static com.spaghettyArts.projectakrasia.utils.Encryption.checkPassword;
import static com.spaghettyArts.projectakrasia.utils.Encryption.hashPassword;
import static com.spaghettyArts.projectakrasia.utils.InputValidation.*;
import static com.spaghettyArts.projectakrasia.utils.RandomString.randomString;

/**
 * O serviço com as funções relativas ao User
 * @author Fabian Nunes
 * @version 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ResetRepository resetRepository;

    @Autowired
    private HistoryRepository historyRepository;

    /**
     * Função para obter um user da base dados pelo seu ID
     * @param id Id do user
     * @return O objeto UserModel com os dados da base de dados
     * @author Fabian Nunes
     */
    public UserModel findByID (Integer id) {
        try {
            Optional<UserModel> obj =  repository.findById(id);
            return obj.get();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Função para obter um user da base dados pelo seu email
     * @param mail Email do user
     * @return O objeto UserModel com os dados da base de dados
     * @author Fabian Nunes
     */
    public UserModel findByMail (String mail) {
        return repository.findUserModelByEmail(mail);
    }

    public UserModel findByUsername (String user) { return repository.findUserModelByUsername(user); }

    /**
     * Função que realiza o login do utilizador. Esta função irá analisar se os dados introduzidos são válidos, e depois
     * disso irá procurar por um user com aquele email, se encontrar será confirmada a password e se esta tiver correta
     * será atualizado o estado user , será gerada uma token de segurança e
     * será atualizado o timestamp da ultima ação sendo depois retornado o objeto.
     * @param email email do user
     * @param password password do user
     * @return Caso seja feito um login válido será enviado para o cliente o objeto UserModel com código 200, caso campos estejam vazios será enviado o 401,
     * se o utilizador não existir será enviado o 404 e se a password não coincidir será enviado o 403.
     * @author Fabian Nunes
     */
    public ResponseEntity<UserModel> login(String email, String password) {
        if(checkEmpty(email) || checkEmpty(password)) {
            return ResponseEntity.badRequest().build();
        }

        UserModel userE =  repository.findUserModelByEmail(email);
        if (userE != null) {
            String paswordH = userE.getPassword();
            if(checkPassword(password, paswordH)) {
                UserModel obj = updateLogin(userE);

                if(obj.getUserOnline() == 0 || obj.getUserOnline() == 1) {
                    obj.setUserOnline(1);
                    String token = randomString(60);
                    obj.setUser_token(token);

                    if (obj.getGot_reward() == 0) {
                        int reward = getDaily(obj.getLogin_reward());
                        obj.setMoney(obj.getMoney() + reward);
                    }

                    obj.setLast_action(Timestamp.from(ZonedDateTime.now().toInstant()));

                    repository.save(obj);
                    return ResponseEntity.ok().body(obj);
                } else {
                    obj.setUserOnline(0);
                    repository.save(obj);
                    return ResponseEntity.status(409).build();
                }
            } else
                return ResponseEntity.status(403).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Função que tratá do registo dos utilizadores, será analisado os dados introduzidos, se os campos foram preenchidos,
     * se o email ou o username já existem e se a pass e username seguem as regras definidoas, caso seja válido será encriptada a password e
     * é criado um objeto UserModel que terá uma data de login default e será guardado na base de dados
     * @param obj Objeto UserModel que possui o email, username e password
     * @return O objeto UserModel criado, caso os inputs não seja válidos será lançada uma excessão.
     * @exception ResponseStatusException Se forem introduzidos campos vazios ou o username ou password
     * não forem válidos será lançado um 401, se já existerm o email ou username será enviado um 409.
     * @author Fabian Nunes
     */
    public UserModel register(UserModel obj) {

        if(checkEmpty(obj.getEmail()) || checkEmpty(obj.getPassword()) || checkEmpty(obj.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        UserModel objU =  repository.findUserModelByUsername(obj.getUsername());
        UserModel objE =  repository.findUserModelByEmail(obj.getEmail());
        if (objE!= null || objU != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            String pass = obj.getPassword();
            if (!checkPasswordInput(pass)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            String username = obj.getUsername();
            if(!checkUsername(username)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            String hash = hashPassword(pass);
            UserModel newUser = new UserModel(obj.getUsername(), hash, obj.getEmail());
            String defaultTime = "1970-01-01";
            try {
                Date defDate = new SimpleDateFormat("yyyy-MM-dd").parse(defaultTime);
                newUser.setLast_login(defDate);
                return repository.save(newUser);
            } catch (ParseException t) {
                System.out.println(t);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    /**
     * Função para o reset da password. Será primeiro analisado o email, token e password se forem válidos será procurado na
     * base de dados na tabela reset por uma linha com o respetivo email e token, caso seja encontrada alterada a palavra passe  no
     * user e será eliminado o registo da tabela reset.
     * @param token o token para confirmar o reset da palavra passe
     * @param email email do user
     * @param password nova palavra passe
     * @return Será retornado o objeto UserModel caso os dados seja válidos
     * @exception ResponseStatusException Se os atributos forem vazios ou invalidos será  retornado um 401, se não existir os campos
     * na tabela user com esse email e na tabela reset o pedido será retornado o 404
     * @author Fabian Nunes
     */
    public UserModel reset(String token, String email, String password) {

        if (checkEmpty(email) || checkEmpty(password) || checkEmpty(token)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(!checkPasswordInput(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ResetModel obj = resetRepository.findByTokenAndEmail(token, email);
        if (obj == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {

            UserModel userObj = repository.findUserModelByEmail(email);
            if (userObj == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            resetRepository.delete(obj);
            String pass = password;
            String hash = hashPassword(pass);
            userObj.setPassword(hash);
            return repository.save(userObj);
        }
    }

    /**
     * Função para alterar o username. Será analisado o input se for válido irá se procurar na base de dados por um user com o id especificado,
     * se existir será atualizado o username e a última ação para este instante
     * @param id id do user
     * @param username novo username
     * @return Será retornado o objeto UserModel caso os dados seja válidos
     * @exception ResponseStatusException Se os atributos forem vazios ou invalidos será  retornado um 401, se já existir
     * o username pretendido será enviado um 409, se o user não exsistir será enviado um 404.
     * @author Fabian Nunes
     */
    public UserModel changeName(int id, String username) {
        if(checkEmpty(username) || !checkUsername(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        UserModel exist = repository.findUserModelByUsername(username);

        if(exist != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }

        UserModel obj = findByID(id);
        if (obj == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        obj.setUsername(username);
        obj.setLast_action(Timestamp.from(ZonedDateTime.now().toInstant()));
        return repository.save(obj);
    }

    /**
     * Função para alterar o nível da armadura e dinheiro do user. Será procurado o user pelo ID, caso exista será
     * atualizado o nível da armadura e o dinheiro que possui.
     * @param id id do user
     * @return Será retornado o codigo 200 caso seja validos os dados, se o user na existir sera mandado o 404 e se
     * o valor de armadura for invalido será enviado um 401
     * @author Fabian Nunes
     */
    public ResponseEntity<UserModel> changeStats(int id) {
        UserModel obj = findByID(id);
        if (obj == null) {
            return ResponseEntity.notFound().build();
        }
        int life = obj.getLife();
        int cost = life * 100;
        if (life < 10 && obj.getMoney() >= cost) {
            life++;
            int money = obj.getMoney() - cost;


            obj.setLife(life);
            obj.setMoney(money);
            obj.setLast_login(new Date());
            obj.setLast_action(Timestamp.from(ZonedDateTime.now().toInstant()));
            repository.save(obj);
            return ResponseEntity.ok().body(obj);
        }
        return ResponseEntity.badRequest().build();
    }


    /**
     * Função para fazer logout, esta função irá atualizar o estado do user e eliminar o token da sesão
     * @param id id do user
     * @return Será retornado o usermodel caso seja válido os dados
     * @exception ResponseStatusException se não existir o user vai ser retornado 404
     * @author Fabian Nunes
     */
    public UserModel logout(int id) {
        UserModel obj = findByID(id);
        if (obj == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        obj.setLast_login(new Date());
        obj.setUserOnline(0);
        obj.setUser_token(null);

        return repository.save(obj);
    }

    /**
     * Função para saber se o request é valido e é feito pelo o user que vai alterar os seus respetivos dados
     * @param id id do user
     * @param token token da sesão
     * @return Será retornado true se o token for igual ao token que está na base de dados para o user especifico senão será false
     * @author Fabian Nunes
     */
    public boolean validateUser(String token, int id) {
        UserModel obj = findByID(id);
        if (obj  == null) {
            return false;
        } else {
            if(obj.getUser_token() == null) {
                return false;
            }
            return obj.getUser_token().equals(token);
        }
    }

    /**
     * Função para obter um user para jogar especifico
     * @return Será retornado o objeto caso exista esse user e ele esteja na loby
     * @author Fabian Nunes
     */
    public UserModel getSUser(String user, Integer myID) {
        UserModel obj = findByUsername(user);
        UserModel myObj = findByID(myID);


        if (obj == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (obj.getId().equals(myID)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if (obj.getUserOnline() == 1) {
            myObj.setLast_action(Timestamp.from(ZonedDateTime.now().toInstant()));
            myObj.setLast_login(new Date());
            repository.save(myObj);

            return new UserModel(obj.getId(), obj.getUsername());
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT);
    }


    /**
     * Função para atualizar o resultado de uma partida multiplayer
     * @param id id do user
     * @param result Resultado da partida 0 - perda 1- vitoria
     * @return Será retornado ok se o utilizador existir
     * @author Fabian Nunes
     */
    public ResponseEntity<Object> matchResult(int id, int result) {
        UserModel obj = findByID(id);
        if (obj  == null) {
            return ResponseEntity.notFound().build();
        } else {

            if (result != 0 && result != 1) {
                return ResponseEntity.badRequest().build();
            }

            int win = obj.getWin();
            int lose = obj.getLose();
            if(result == 1) {
                win ++;
                obj.setWin(win);
                obj.setMoney(obj.getMoney() + 100);
            } else {
                lose++;
                obj.setLose(lose);
            }

            int rank = Matches.defineRank(lose, win);
            obj.setRank(rank);
            obj.setLast_action(Timestamp.from(ZonedDateTime.now().toInstant()));
            obj.setLast_login(new Date());
            obj.setUserOnline(1);
            repository.save(obj);
            return ResponseEntity.ok().body(obj);
        }
    }

    /**
     * Função para atualizar o estado do jogador
     * @param id id do user
     * @param state Estado do user
     * @return Será retornado ok se o utilizador existir
     * @author Fabian Nunes
     */
    public ResponseEntity<Object> changeState(int id, int state) {
        UserModel obj = findByID(id);
        if (obj == null) {
            return ResponseEntity.notFound().build();
        }

        if (state >= 0 && state <= 3) {
            obj.setUserOnline(state);
            obj.setLast_action(Timestamp.from(ZonedDateTime.now().toInstant()));
            obj.setLast_login(new Date());
            repository.save(obj);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


    /**
     * A função irá criar guardar o jogo na base de dados
     * @param winer id do vencedor
     * @param loser id do perdedor
     * @author Fabian Nunes
     * @return
     */
    public ResponseEntity<Object> endMatch(int winer, int loser) {


        UserModel winner = findByID(winer);
        UserModel lost = findByID(loser);
        if (winner == null || lost == null) {
            return ResponseEntity.notFound().build();
        } else {
            HistoryModel obj = new HistoryModel(winer, loser);
            historyRepository.save(obj);
            return ResponseEntity.status(201).build();
        }
    }
}
