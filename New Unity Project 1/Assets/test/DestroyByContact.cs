using UnityEngine;
using System.Collections;

public class DestroyByContact : MonoBehaviour {

    int cnt = 0;
    public int howMuchAttack;
    public GameObject explosion;
    public GameObject playerExplosion;

    private GameController gameController;
    public int Score;

    AudioSource astroidDestroy;

    void Start()
    {
        astroidDestroy = GetComponent<AudioSource>();
        GameObject gameControllerObject = GameObject.FindWithTag("GameController");
        if(gameControllerObject != null)
        {
            gameController = gameControllerObject.GetComponent<GameController>();
        }
        if (gameController == null)
            print("Not find GameController");
    }

    void OnTriggerEnter(Collider other)
    {
        if(other.gameObject.tag == "Boundary")
        {
            return;
        }
        if (other.gameObject.tag == "Player")
        {
            Instantiate(playerExplosion, transform.position, transform.rotation);
            gameController.SetGameOver();
            gameController.SetRestart();
        }
        Destroy(other.gameObject);
        gameController.AddScore(Score);
        cnt++;
        if (cnt >= howMuchAttack)
        {
            astroidDestroy.Play();
            cnt = 0;
            gameController.AddScore(Score * cnt);
            Instantiate(explosion, transform.position, transform.rotation);
            Destroy(gameObject);
        }
    }
}
