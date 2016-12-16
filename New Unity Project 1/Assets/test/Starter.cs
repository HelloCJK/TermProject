using UnityEngine;
using System.Collections;
using UnityEngine.SceneManagement;

public class Starter : MonoBehaviour {

    void Awake()
    {
        Screen.sleepTimeout = SleepTimeout.NeverSleep;
        Screen.fullScreen = true;
        Screen.orientation = ScreenOrientation.Landscape;
    }

    // Use this for initialization
    void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
        if (Input.touchCount > 0 || Input.GetKey(KeyCode.Space) )
        {
            StartCoroutine(LoadDelay());
        }
    }
    IEnumerator LoadDelay()
    {
        yield return new WaitForSeconds(1.0f);
        SceneManager.LoadScene("test1");
    }
}
